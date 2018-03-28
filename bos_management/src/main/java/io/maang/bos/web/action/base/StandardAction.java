package io.maang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import io.maang.bos.domain.base.Standard;
import io.maang.bos.service.base.StandardService;
import lombok.Setter;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 收派标准的action
 */
@ParentPackage("json-default")
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

	@Autowired
	private StandardService standardService;



	//查询操作
	@Action(value = "standard_getOne",
			results = {@Result(name = "success",type = "redirect",
					location = "/bos_management/pages/base/standard.html")})
	public java.lang.String getOne() {
		Integer id = 2;
		Standard one = standardService.getOne(id);
		System.out.println(one);
		return SUCCESS;
	}

	//添加操作
	@Action(value = "standard_save",
			results = {@Result(name = "success",type = "redirect",
					location = "/bos_management/pages/base/standard.html")})
	public java.lang.String save() {
		standardService.save(standard);
		return SUCCESS;
	}

	@Setter
	private Integer page;
	@Setter
	private Integer rows;

	//分页列表查询
	@Action(value = "standard_pageQuery",results = {@Result(name = "success",type = "json")})
	public String pageQuery() {

		//调用业务层查询结果

		//1.调用业务层查询当前页的数据
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> standards = standardService.findPageData(pageable);

		//2.返回客户端需要的total 和 rows
		Map<String, Object> map = new HashMap<>();
		map.put("total", standards.getTotalElements());
		map.put("rows", standards.getContent());

		//3.将map转换为json数据返回 使用struts2提供的插件
		ActionContext.getContext().getValueStack().push(map);


		return SUCCESS;
	}


}
