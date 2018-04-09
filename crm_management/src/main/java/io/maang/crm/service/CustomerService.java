package io.maang.crm.service;

import io.maang.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-02 19:34
 */
public interface CustomerService {

    //查询所有的未关联的客户列表\
    @GET
    @Path("/noassociationcustomers")
    @Produces({"application/xml", "application/json"})
     List<Customer> findNoAssociationCustomers();

    //查询已关联的客户列表
    @GET
    @Path("/associationfixedareacustomers/{fixedareaid}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
     List<Customer> findHasAssociationFixedAreaCustomers(
            @PathParam("fixedareaid") String fixedAreaId);

    //将客户关联到定区上 并将所有的客户id拼接成1,2,3
    @PUT
    @Path("/associationcustomertofixedarea")
    @Consumes({"application/xml", "application/json"})
     void associationCustomerToFixedAreaId(
            @QueryParam("customerIdStr") String customerIdStr,
            @QueryParam("fixedAreaId") String fixedAreaId);

    @POST
    @Path("/customer")
    @Consumes({"application/xml","application/json"})
     void regist(Customer customer);

    @GET
    @Path("/customer/telephone/{telephone}")
    @Consumes({"application/xml","application/json"})
    @Produces({"application/xml","application/json"})
     Customer findByTelephone(@PathParam("telephone") String telephone);

    @PUT
    @Path("/customer/updatetype/{telephone}")
    @Consumes({"application/xml","application/json"})
     void updateType(@PathParam("telephone") String telephone);



}
