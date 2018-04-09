package io.maang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import io.maang.bos.domain.base.FixedArea;
import io.maang.bos.service.base.FixedAreaService;
import io.maang.bos.web.action.common.BaseAction;
import io.maang.crm.domain.Customer;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    //属性驱动
    @Setter
    private String[] customerIds;
    //定区关联取派员
    @Setter
    private Integer courierId;
    @Setter
    private Integer takeTimeId;



    //注入service
    @Autowired
    private FixedAreaService fixedAreaService;

    //保存定区
    @Action("fixedArea_save")
    public String save() {
        fixedAreaService.save(model);
        return NONE;
    }

    //带条件的查询
    @Action(value = "fixedArea_pageQuery", results = {@Result(name = "success", type = "json")})
    public String pageQuery() {
        //构造分页的查询对象
        Pageable pageable = new PageRequest(page - 1, rows);
        //构造查询的条件对象
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //构造查询条件
                //根据定区编号等值查询
                if (StringUtils.isNotBlank(model.getId())) {
                    Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCompany())) {
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
                    list.add(p2);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };

        //调用业务层
        Page<FixedArea> pageData = fixedAreaService.findPageData(specification, pageable);
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

    //查询未关联定区列表
    @Action(value = "fixedArea_findNoAssociationCustomer", results = {@Result(name = "success", type = "json")})
    public String findNoAssociationCustomer() {
        //使用webclient调用webService接口
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:9002/crm_management/services/customerService/noassociationcustomers")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);
        return SUCCESS;
    }

    //查询关联定区列表
    @Action(value = "fixedArea_findHasAssociationFixedAreaCustomer", results = {@Result(name = "success", type = "json")})
    public String findHasAssociationFixedAreaCustomer() {
        //使用webclient调用webService接口
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:9002/crm_management/services/customerService/associationfixedareacustomers/" + model.getId())
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);
        return SUCCESS;
    }

    //关联客户到定区
    @Action(value = "fixedArea_associationCustomerToFixedArea", results = {@Result(name = "success", type = "redirect",
            location = "/pages/base/fixed_area.html")})
    public String associationCustomerToFixedArea() {
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient
                .create("http://localhost:9002/crm_management/services/customerService/" +
                        "associationcustomertofixedarea?customerIdStr=" + customerIdStr
                        + "&fixedAreaId=" + model.getId())
                .put(null);

        return SUCCESS;
    }
    //关联快递员到定区
    @Action(value = "fixedArea_associationCourierToFixedArea", results = {@Result(name = "success", type = "redirect",
            location = "/pages/base/fixed_area.html")})
    public String associationCourierToFixedArea() {
        //调用业务层关联快递员
        fixedAreaService.associationCourierToFixedArea(model, courierId,takeTimeId);

        return SUCCESS;
    }


}