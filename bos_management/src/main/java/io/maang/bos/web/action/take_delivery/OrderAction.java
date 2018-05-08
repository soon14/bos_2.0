package io.maang.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import io.maang.bos.domain.take_delivery.Order;
import io.maang.bos.service.take_delivery.OrderService;
import io.maang.bos.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 订单
 *
 * @outhor ming
 * @create 2018-04-20 12:13
 */
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class OrderAction extends BaseAction<Order> {

    @Autowired
    private OrderService orderService;

    @Action(value = "order_findByOrderNum",results = {@Result(name = "success",type = "json")})
    public String findByOrderNum() {
        //调用业务查询order信息
        Order order = orderService.findByOrderNum(model.getOrderNum());
        Map<String,Object> result = new HashMap<>();
        if (order == null) {
            //订单不存 在
            result.put("success", false);
        } else {
            //订单存在
            result.put("success", true);
            result.put("orderData", order);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}
