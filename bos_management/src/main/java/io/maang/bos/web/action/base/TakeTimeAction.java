package io.maang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import io.maang.bos.domain.base.TakeTime;
import io.maang.bos.service.base.TakeTimeService;
import io.maang.bos.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 描述:
 * 取派员
 *
 * @outhor ming
 * @create 2018-03-29 20:15
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {


    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "taketime_findAll",results = {@Result(name = "success",type = "json")})
    public String findAll() {
        //调用业务层查询所有的收派时间
        List<TakeTime> takeTimes = takeTimeService.findAll();
        //压入值栈
        ActionContext.getContext().getValueStack().push(takeTimes);
        return SUCCESS;
    }



}