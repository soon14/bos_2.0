package io.maang.bos.web.action;

import io.maang.bos.domain.base.Area;
import io.maang.bos.domain.constant.Constant;
import io.maang.bos.domain.take_delivery.Order;
import io.maang.crm.domain.Customer;
import lombok.Setter;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

/**
 * 描述:
 *      前端系统订单处理数据
 * @outhor ming
 * @create 2018-04-17 21:18
 */
@ParentPackage("json-default")
@Scope("prototype")
@Controller
@Namespace("/")
@Setter
public class OrderAction extends BaseAction<Order> {

    private String sendAreaInfo; //发件人的省市区信息

    private String recAreaInfo; //收件人的省市区信息

    @Action(value = "order_add",results = {@Result(name = "success",type = "redirect",location = "index.html")})
    public String add() {

        //手动封装area
        Area sendArea = new Area();
        String[] sendAreaData = sendAreaInfo.split("/");
        sendArea.setProvince(sendAreaData[0]);
        sendArea.setCity(sendAreaData[1]);
        sendArea.setDistrict(sendAreaData[2]);

        Area recArea = new Area();
        String[] recAreaData = recAreaInfo.split("/");
        recArea.setProvince(recAreaData[0]);
        recArea.setCity(recAreaData[1]);
        recArea.setDistrict(recAreaData[2]);

        model.setRecArea(recArea);
        model.setSendArea(sendArea);

        //关联当前登陆的用户
        Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
        model.setCustomer_id(customer.getId());

        //调用webservice 将数据传递给bos_management系统
        WebClient
                .create(Constant.BOS_MANAGEMENT_URL+"/services/orderService/order")
                .type(MediaType.APPLICATION_JSON)
                .post(model);

        return SUCCESS;
    }
}
