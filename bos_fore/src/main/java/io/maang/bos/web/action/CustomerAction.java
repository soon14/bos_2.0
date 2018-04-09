package io.maang.bos.web.action;

import io.maang.bos.utils.SmsUtils;
import io.maang.crm.domain.Customer;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;

/**
 * 描述:
 *
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

        //调用sms服务发送短信
        String result = SmsUtils.sendSmsByHTTP(model.getMobilePhone(), msg);
        if (result.startsWith("000")) {
            //发送成功
            return NONE;
        } else {
            throw new RuntimeException("短信发送失败,信息码是:" + result);
        }
    }


    @Action(value = "customer_regist",results={
            @Result(name = "success",type = "redirect",location = "signup-success.html"),
            @Result(name = "input",type = "redirect",location = "signup.html")})
    public String regist(){

        //先校验短信验证码,如果不通过则返回注册页面
        //从session中获取之前生成的验证码
        String checkcodeSession = (String)  ServletActionContext.getServletContext().getAttribute(model.getTelephone());
        if (checkcodeSession==null||!checkcodeSession.equals(checkcode)){
            System.out.println("短信验证码错误");
            return INPUT;
        }
        //调用webservice 连接crm保存客户
        WebClient
                .create("http://localhost:9002/crm_management/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);
        System.out.println("客户注册成功");

        return SUCCESS;
    }
}