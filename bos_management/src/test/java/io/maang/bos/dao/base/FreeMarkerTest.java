package io.maang.bos.dao.base;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-13 14:40
 */
public class FreeMarkerTest {

    @Test
    //todo 测试freemarker静态页面
    public void testName() throws Exception {
        //配置对象,配置模板位置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        //指向模板的位置
        configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/templates"));
        Template template = configuration.getTemplate("hello.ftl");
        //动态数据对象
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("title", "测试成功");
        stringObjectHashMap.put("msg", "没毛病");
        //输出控制台
        template.process(stringObjectHashMap,new PrintWriter(System.out));
    }

    @Test
    public void test() throws Exception {



    }
}
