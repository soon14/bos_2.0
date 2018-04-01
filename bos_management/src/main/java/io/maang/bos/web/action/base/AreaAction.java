package io.maang.bos.web.action.base;

import io.maang.bos.domain.base.Area;
import io.maang.bos.service.base.AreaService;
import io.maang.bos.utils.PinYin4jUtils;
import io.maang.bos.web.action.common.BaseAction;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class AreaAction extends BaseAction<Area> {


    //注入业务对象
    @Autowired
    private AreaService areaService;
    //接受上传文件
    @Setter
    private File file;

    //批量区域数据导入
    @Action("area_batchImport")
    public String batchImport() throws IOException, InvalidFormatException {
        List<Area> areas = new ArrayList<>();
        //编写解析代码的逻辑
        //1.加载excle文件对象
        Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
        //2.读取一个sheet
        Sheet sheetAt = workbook.getSheetAt(0);
        //3.读取sheet的每一行
        for (Row cells : sheetAt) {
            //一行数据对应一个区域对象
            if (cells.getRowNum() == 0) {
                //第一行跳过
                continue;
            }
            //跳过空行
            if (cells.getCell(0) == null
                    || StringUtils.isBlank(cells.getCell(0).getStringCellValue())) {
                continue;
            }
            Area area = new Area();
            area.setId(cells.getCell(0).getStringCellValue());
            area.setProvince(cells.getCell(1).getStringCellValue());
            area.setCity(cells.getCell(2).getStringCellValue());
            area.setDistrict(cells.getCell(3).getStringCellValue());
            area.setPostcode(cells.getCell(4).getStringCellValue());
            //基于pinyin4j生成城市编码和简码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            //简码
            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : headArray) {
                stringBuffer.append(s);
            }
            String shortcode = stringBuffer.toString();
            area.setShortcode(shortcode);

            //城市的编码
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);

            areas.add(area);
        }
        //调用业务层
        areaService.saveBatch(areas);
        return NONE;
    }


    //提高效率使用params过滤掉subareas
    @Action(value = "area_pageQuery", results = {@Result(name = "success",
            type = "json",params = {"excludeProperties","  rows\\[\\d+\\].subareas"})})
    public String pageQuery() {

        //构造分页的查询对象
        Pageable pageable = new PageRequest(page - 1, rows);
        //构造条件查询对象
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建条件的集合对象
                List<Predicate> predicateList = new ArrayList<>();

                //添加条件
                if (StringUtils.isNotBlank(model.getProvince())) {
                    Predicate p1 = cb.like(root.get("province")
                            .as(String.class), "%" + model.getProvince() + "%");
                    predicateList.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCity())) {
                    Predicate p2 = cb.like(root.get("city")
                            .as(String.class), "%" + model.getCity() + "%");
                    predicateList.add(p2);
                }
                if (StringUtils.isNotBlank(model.getDistrict())) {
                    Predicate p3 = cb.like(root.get("district")
                            .as(String.class), "%" + model.getDistrict() + "%");
                    predicateList.add(p3);
                }


                return cb.and(predicateList.toArray(new Predicate[0]));
            }
        };
        //调用业务层完成操作
        Page<Area> pageData = areaService.findPageData(specification, pageable);
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }


}