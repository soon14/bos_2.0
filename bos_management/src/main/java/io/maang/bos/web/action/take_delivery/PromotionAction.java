package io.maang.bos.web.action.take_delivery;

import io.maang.bos.domain.take_delivery.Promotion;
import io.maang.bos.service.take_delivery.PromotionService;
import io.maang.bos.web.action.common.BaseAction;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 描述:
 * 宣传活动
 *
 * @outhor ming
 * @create 2018-04-11 12:31
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

 	@Setter
    private File titleImgFile;
    @Setter
    private String titleImgFileFileName;

    @Autowired
	private PromotionService promotionService;

    @Action(value = "promotion_save", results = {
    		@Result(name = "success",
					type = "redirect",location = "/pages/take_delivery/promotion.html")})
    public String save() throws IOException {
    	String savePath = ServletActionContext.getServletContext().getRealPath("/upload/");
        String saveUrl = ServletActionContext.getRequest().getContextPath() + "/upload/";
        //生成随机的文件名
        UUID uuid = UUID.randomUUID();
        String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
        String randomFileName = uuid + ext;
        //保存图片
        FileUtils.copyFile(titleImgFile,new File(savePath+"/"+randomFileName) );

        //保存路径 将相对工程访问路径保存到model中
		model.setTitleImg(saveUrl+randomFileName);
		//调用业务层 完成活动任务数据保存
		promotionService.save(model);

        return SUCCESS;
    }


    @Action(value = "promotion_pageQuery", results = {
    		@Result(name = "success",type = "json")})
    public String pageQuery() {
    	//构造分页查询的参数
		Pageable pageable = new PageRequest(page - 1, rows);
		//调用业务层完成查询
		Page<Promotion> pageData = promotionService.findPageData(pageable);
		//压入值栈
		pushPageDataToValueStack(pageData);

        return SUCCESS;
    }



}
