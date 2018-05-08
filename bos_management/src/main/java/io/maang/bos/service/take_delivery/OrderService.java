package io.maang.bos.service.take_delivery;

import io.maang.bos.domain.take_delivery.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface OrderService {


    @POST
    @Consumes({"application/json","application/xml"})
    @Path("/order")
    void saveOrder(Order order);

    Order findByOrderNum(String orderNum);
}
