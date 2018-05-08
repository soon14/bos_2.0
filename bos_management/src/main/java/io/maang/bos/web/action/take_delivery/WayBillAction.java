package io.maang.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import io.maang.bos.domain.take_delivery.WayBill;
import io.maang.bos.service.take_delivery.WayBillService;
import io.maang.bos.web.action.common.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 运单的快速录入
 *
 * @outhor ming
 * @create 2018-04-19 15:44
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {

    private final static Logger LOGGER = Logger.getLogger(WayBillAction.class);

    @Autowired
    private WayBillService wayBillService;

    @Action(value = "wayBill_save", results = {@Result(name = "success", type = "json")})
    public String save() {
        //去除没有id的order对象
        if (model.getOrder() != null && (model.getOrder().getId() == null || model.getOrder().getId() == 0)) {
            model.setOrder(null);
        }
        Map<String, Object> result = new HashMap<>();
        try {
            wayBillService.save(model);
            //保存成功
            result.put("success", true);
            result.put("msg", "保存运单成功");
            LOGGER.info("保存运单成功,运单号:" + model.getWayBillNum());
        } catch (Exception e) {
            e.printStackTrace();
            //保存失败
            result.put("success", false);
            result.put("msg", "运单保存失败");
            LOGGER.error("保存运单失败,运单号是:" + model.getWayBillNum(), e);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    @Action(value = "waybill_pageQuery", results = {@Result(name = "success", type = "json")})
    public String pageQuery() {

        //无条件查询
        Pageable pageable = new PageRequest(page - 1, rows,
                new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        //调用业务层查询
        Page<WayBill> pageData = wayBillService.findPageData(model, pageable);
        //压入值栈
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

    @Action(value = "waybill_findByWayBillNum", results = {@Result(name = "success", type = "json")})
    public String findByWayBillNum() {

        //调用业务层查询
        WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
        Map<String, Object> result = new HashMap<>();
        if (wayBill == null) {
            //运单不存在
            result.put("success", false);
        } else {
            //运单存在
            result.put("success", true);
            result.put("wayBillData", wayBill);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

}
