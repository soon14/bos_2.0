package io.maang.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.maang.bos.domain.constant.Constant;
import io.maang.bos.domain.page.PageBean;
import io.maang.bos.domain.take_delivery.Promotion;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.HashMap;

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
@SuppressWarnings("all")
public class PromotionAction extends BaseAction<Promotion> {

    @Action(value = "promotion_pageQuery",
            results = {@Result(name = "success",type = "json")})
    public String pageQuery() {

        //基于webservice,获取bos_management活动列表和数据信息
        PageBean<Promotion> pageBean = WebClient
                .create("http://localhost:9001/bos_management/services/promotionService/pageQuery?page="+page+"&rows="+rows)
                .accept(MediaType.APPLICATION_JSON)
                .get(PageBean.class);
        ActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }

    @Action(value = "promotion_showDetail",
            results = {@Result(name = "success",type = "json")})
    public String showDetail() throws IOException, TemplateException {

        //判断对应id是否存在对应的html, 存在直接返回
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/freemarker");
        File htmlFile = new File(htmlRealPath + "/" + model.getId() + ".html");
        //不存在直接查询数据库
        if (!htmlFile.exists()) {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            configuration.setDirectoryForTemplateLoading(new File(
                    ServletActionContext.getServletContext().getRealPath(
                            "/WEB-INF/freemarker_templates")));
            //获取模板对象
            Template template = configuration.getTemplate("promotion_details.ftl","utf-8");

            //动态数据
              Promotion promotion = WebClient
                .create(Constant.BOS_MANAGEMENT_URL+"/bos_management/services/promotionService/promotion/"+model.getId())
                .accept(MediaType.APPLICATION_JSON)
                .get(Promotion.class);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("promotion", promotion);
            //合并输出
            template.process(map, new OutputStreamWriter(new FileOutputStream(htmlFile),"utf-8" ));

        }
        //存在直接返回
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        FileUtils.copyFile(htmlFile, ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }

}