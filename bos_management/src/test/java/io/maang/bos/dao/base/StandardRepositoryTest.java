package io.maang.bos.dao.base;

import io.maang.bos.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;

    //测试懒加载
    //javassist冲突解决问题
    //could not initialize proxy - no Session问题  添加OpenEntityManagerInViewFilter
//    懒加载的问题解决
//    注意:一定要放在struts2过滤器上面
//            否则还是会产生nosession问题
    @Test
    public void getOneTest(){
        //需要服务器启动才可测试
        System.out.println("hell年后吃饭");
//        Standard one = standardRepository.getOne(2);
//        System.out.println(one);


    }

    //测试查询一个
    @Test
    public void findOneTest(){

        Standard one = standardRepository.findOne(2);
        System.out.println(one);


    }

    //测试查询所有
    @Test
    public void queryTest(){

        List<Standard> list = standardRepository.findAll();
        System.out.println(list);
    }
    //测试修改一个收派标准
    @Test
    public void updeTest(){

        Standard standard = new Standard();
        standard.setId(4);
        standard.setMaxLength(11);
        standard.setMaxWeight(11);
        standard.setName("修改test..update...");
        standardRepository.save(standard);

    }
    //测试删除一个收派标准
    @Test
    public void deleTest(){

        standardRepository.delete(1);

    }

    //测试保存一个收派标准
    @Test
    public void saveTest(){

        Standard standard = new Standard();
        standard.setMaxLength(11);
        standard.setMaxWeight(11);
        standard.setName("test...");
        standardRepository.save(standard);

    }

}