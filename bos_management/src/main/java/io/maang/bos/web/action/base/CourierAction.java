package io.maang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import io.maang.bos.domain.base.Courier;
import io.maang.bos.service.base.CourierService;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    Courier courier = new Courier();  //封装快递员

    @Setter
    private Integer page;   //封装当前页
    @Setter
    private Integer rows;   //封装每页多少行
    @Setter
    private String ids;     //封装作废快递员的id字符串

    @Autowired
    private CourierService courierService;   //注入业务层

    @Override
    public Courier getModel() {
        return courier;
    }

    //添加取派员方法
    @Action(value = "courier_save")
    public String save() {
        courierService.save(courier);
        return NONE;
    }

    //无条件分页查询
    @Action(value = "courier_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery() {

        //根据查询条件 构造specification条件查询对象 (雷士hibernate的qbc查询)
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            /**
             * 构造条件查询方法,如果返回null代表无条件查询
             * Root 参数 获取条件表达式name=?,age=?
             * CriteriaQuery 构造简单查询条件返回,提供where 方法
             *  CriteriaBuilder 构造predicate对象,条件对象,构造复杂查询效果
             */
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();
                //单表查询  查询当前对象 对应数据表
                if (StringUtils.isNotBlank(courier.getCourierNum())){
                    //进行员工 工号查询courierNum
                    Predicate p1 = cb.equal(
                            root.get("courierNum").as(String.class),
                            courier.getCourierNum());
                   // System.out.println(root.get("courierNum"));
                    list.add(p1);
                }

                if (StringUtils.isNotBlank(courier.getCompany())){
                    //进行公司查询的模糊查询
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(courier.getType())){
                    //进行快递员类型查询 等值查询

                    Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
                    list.add(p3);
                }

                //多表查询 查询当前对象  关联对象对应数据表
                //使用courier(Root),关联Standard
                Join<Object, Object> standardRoot = root.join("standard", JoinType.INNER);
                if (courier.getStandard() != null && StringUtils.isNotBlank(courier.getStandard().getName())) {
                    //进行收派标准的模糊查询
                    Predicate p4 = cb.like(standardRoot.get("name").as(String.class), "%"+courier.getStandard().getName()+"%");
                    list.add(p4);
                }


                return cb.and(list.toArray(new Predicate[0]));

            }
        };
        //封装pageable对象
        Pageable pageable = new PageRequest(page - 1, rows);

        //调用业务层返回page
       Page<Courier> pageDate = courierService.findPageDate(specification,pageable);
        //将page对象转换成datagrid需要的格式
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", pageDate.getTotalPages());
        result.put("rows", pageDate.getContent());
        //将结果压入栈顶
        ActionContext.getContext().getValueStack().push(result);

        /*
        查询所有

        //封装pageable对象
        Pageable pageable = new PageRequest(page - 1, rows);
        //调用业务层返回page
        Page<Courier> page = courierService.findPageDate(pageable);
        //将page对象转换成datagrid需要的格式
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotalPages());
        result.put("rows", page.getContent());
        //将结果压入栈顶
        ActionContext.getContext().getValueStack().push(result);

        */


        return SUCCESS;
    }


    //作废快递员
    @Action(value = "courier_delBatch",
            results = {@Result(name = "success",type = "redirect",
                    location = "/pages/base/courier.html")})
    public String delBatch() {
        String[] idArray = ids.split(",");
        courierService.delBatch(idArray);
        return SUCCESS;
    }
     //还原快递员
    //作废快递员
    @Action(value = "courier_restoreBatch",
            results = {@Result(name = "success",type = "redirect",
                    location = "/pages/base/courier.html")})
    public String restoreBatch() {
        String[] idArray = ids.split(",");
        courierService.restoreBatch(idArray);
        return SUCCESS;
    }

    //查询没有关联的快递员
    @Action(value = "courier_findnoassociation",results = {@Result(name = "success",type = "json")})
    public String findNoAssociation() {

        //调用业务层查询没有关联的快递员
        List<Courier> couriers = courierService.findNoAssociation();
        //将快递员压入值栈
        ActionContext.getContext().getValueStack().push(couriers);
        return SUCCESS;
    }

}