package io.maang.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 抽取action的公共代码,简化开发
 *
 * @outhor ming
 * @create 2018-04-1 20:15
 */

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    //模型驱动
    protected T model;

    @Override
    public T getModel() {
        return model;
    }

    //构造器完成model的实例化
    public BaseAction() {
        //构造子类的对象,获取继承的父类的型的泛型
        //AreaAction extend BaseAction<Area>
        //BaseAction<Area>
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型第一个泛型的参数
        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        try {
           model = modelClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("模型构造失败...");
        }
    }

    //分页查询
   //带条件的区域查询
    @Setter
    protected Integer page;
    @Setter
    protected Integer rows;
    //将分页查询结果数据,压入值栈的方法
    protected void pushPageDataToValueStack(Page<T> pageData){
        //根据查询结果封装到,datagrid需要的数据表格
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotalElements());
        result.put("rows", pageData.getContent());
        //压入值栈返回
        ActionContext.getContext().getValueStack().push(result);
    }
}