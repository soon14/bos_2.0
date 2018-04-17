package io.maang.bos.web.action;

import io.maang.bos.domain.constant.Constant;
import io.maang.bos.utils.MailUtils;
import io.maang.crm.domain.Customer;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-03-29 20:15
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

    @Setter
    private String checkcode;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Action("customer_sendSms")
    public String sendSms() throws UnsupportedEncodingException {
        //手机号保存到客户对象中
        //生成短信验证码
        String randomCode = RandomStringUtils.randomNumeric(4);
        //将短信验证码放到session中
        ServletActionContext.getServletContext().setAttribute(model.getTelephone(), randomCode);
        System.out.println("生成的短信验证码是" + randomCode);

        //编辑短信内容
        String msg = "尊敬的用户本次您的验证码是:" + randomCode + ",服务电话是:13242342";

        //调用mq服务发送一条消息
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage =  session.createMapMessage();
                mapMessage.setString("telephone",model.getTelephone() );
                mapMessage.setString("msg",msg );
                return mapMessage;
            }
        });
        return NONE;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Action(value = "customer_regist", results = {
            @Result(name = "success", type = "redirect", location = "signup-success.html"),
            @Result(name = "input", type = "redirect", location = "signup.html")})
    public String regist() {

        //先校验短信验证码,如果不通过则返回注册页面
        //从session中获取之前生成的验证码
        String checkcodeSession = (String) ServletActionContext.getServletContext().getAttribute(model.getTelephone());
        if (checkcodeSession == null || !checkcodeSession.equals(checkcode)) {
            System.out.println("短信验证码错误");
            return INPUT;
        }
        //调用webservice 连接crm保存客户
        WebClient
                .create(Constant.CRM_MANAGEMENT_URL+"/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);

        System.out.println("客户注册成功");
        //发送一个激活邮箱
        //生成激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        //将激活码存到redis中,24小时失效
        redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);
        //调用mailutils发送邮件
        String content = "尊敬的客户您好,请您于24小时进行邮箱绑定,点击下面链接完成绑定:<br/><a href='"
                + MailUtils.activeUrl + "?telephone=" + model.getTelephone() + "&activeCode=" + activeCode + "'>速运快递绑定邮箱</a>";
        MailUtils.sendMail("速运快递激活邮件", content, model.getEmail());

        return SUCCESS;
    }

    @Setter
    private String activeCode;

    //激活邮箱
    @Action("customer_activeMail")
    public void activeMail() throws IOException {
        //处理乱码的问题
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        //判断激活码是否有效
        String acticeCodeRedis = redisTemplate.opsForValue().get(model.getTelephone());
        if (acticeCodeRedis == null || !acticeCodeRedis.equals(activeCode)) {
            //激活码无效
            ServletActionContext.getResponse().getWriter().println("激活码无效,请登陆系统重新绑定邮箱!");
        } else {
            //激活码有效
            //防止重复绑定
            //调用crm webservice 查询客户信息,判读是否绑定了
            Customer customer = WebClient
                    .create(Constant.CRM_MANAGEMENT_URL+"/services/customerService/customer/telephone/"+model.getTelephone())
                    .accept(MediaType.APPLICATION_JSON)
                    .get(Customer.class);
            if (customer.getType()==null||customer.getType()!=1){
                //没有绑定 进行绑定
                WebClient
                        .create(Constant.CRM_MANAGEMENT_URL+"/services/customerService/customer/updatetype/"+model.getTelephone())
                        .accept(MediaType.APPLICATION_JSON)
                        .put(null);
                ServletActionContext.getResponse().getWriter().println("恭喜你绑定成功!");
            } else {
                //绑定过了
                ServletActionContext.getResponse().getWriter().println("您已经绑定了,请不要重复绑定!");
            }
            //删除redis的激活码
            redisTemplate.delete(model.getTelephone());

        }
    }

    //客户登陆
    @Action(value = "customer_login",results = {
            @Result(name = "login",location = "login.html",type = "redirect"),
            @Result(name = "success",location = "index.html#/myhome",type = "redirect")})
    public String login() {
        Customer customer = WebClient
                .create(Constant.CRM_MANAGEMENT_URL
                        +"/services/customerService/customer/login?telephone="
                        +model.getTelephone()+"&password="+model.getPassword())
                .accept(MediaType.APPLICATION_JSON)
                .get(Customer.class);
        if (customer == null) {
            //登陆失败
            System.out.println("登陆失败");
        }else {
            //登陆成功
            ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
        }
        return SUCCESS;
    }
}