package io.maang.bos.web.action.base;

import io.maang.bos.domain.base.FixedArea;
import io.maang.bos.service.base.FixedAreaService;
import io.maang.bos.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 描述:
 * 定区
 *
 * @outhor ming
 * @create 2018-04-1 20:15
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {


    //注入service
    @Autowired
    private FixedAreaService fixedAreaService;

    //保存定区
    @Action("fixedArea_save")
    public String save() {

        fixedAreaService.save(model);
        return NONE;
    }


}