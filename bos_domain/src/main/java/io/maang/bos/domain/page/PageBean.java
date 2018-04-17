package io.maang.bos.domain.page;

import io.maang.bos.domain.take_delivery.Promotion;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * 描述:
 * page 和 rows 返回分页数据 封装
 *
 * @outhor ming
 * @create 2018-04-13 19:08
 */
@XmlRootElement(name = "pageBean")
@Setter@Getter
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    private Long totalCount;//总记录数
    private List<T> pageData;//当前页数据


}
