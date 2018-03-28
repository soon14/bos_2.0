package io.maang.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import io.maang.bos.domain.base.Standard;
import org.apache.struts2.convention.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 收派标准的action
 */
@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	//模型驱动
	private Standard standard = new Standard();

	@Override
	public Standard getModel() {
		return standard;
	}

	@Action(value = "standard_save",
			results = {@Result(name = "success",type = "redirect",
					location = "./pages/base/standard.html")})
	public String save() {
		System.out.println("添加了收排标准"+standard.getName());

		return SUCCESS;
	}
}
