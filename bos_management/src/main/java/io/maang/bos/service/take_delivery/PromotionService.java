package io.maang.bos.service.take_delivery;

import io.maang.bos.domain.page.PageBean;
import io.maang.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import java.util.Date;

public interface PromotionService {
    //保存宣传任务
    void save(Promotion promotion);

    //分页查询
    Page<Promotion> findPageData(Pageable pageable);

    //根据page和rows返回分页数据
    @Path("/pageQuery")
    @GET
    @Produces({"application/json","application/xml"})
    @Consumes({"application/json","application/xml"})
    PageBean<Promotion> pageQuery(@QueryParam("page") Integer page, @QueryParam("rows") Integer rows);

    @Path("/promotion/{id}")
    @GET
    @Produces({"application/json","application/xml"})
    Promotion findById(@PathParam("id") Integer id);

    //设置活动过期
    void updeStatus(Date date);

}
