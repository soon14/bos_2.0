package io.maang.bos.domain.take_delivery;

import io.maang.bos.domain.constant.Constant;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:促销信息实体类
 */
@Entity
@Table(name = "T_PROMOTION")
@Data
@XmlRootElement(name = "promotion")
public class Promotion implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    private Integer id;
    @Column(name = "C_TITLE")
    private String title; // 宣传概要(标题)
    @Column(name = "C_TITLE_IMG")
    private String titleImg; // 宣传图片
    @Column(name = "C_ACTIVE_SCOPE")
    private String activeScope;// 活动范围
    @Column(name = "C_START_DATE")
    private Date startDate; // 发布时间
    @Column(name = "C_END_DATE")
    private Date endDate; // 失效时间
    @Column(name = "C_UPDATE_TIME")
    private Date updateTime; // 更新时间
    @Column(name = "C_UPDATE_UNIT")
    private String updateUnit; // 更新单位
    @Column(name = "C_UPDATE_USER")
    private String updateUser;// 更新人 后续与后台用户关联
    @Column(name = "C_STATUS")
    private String status = "1"; // 状态 可取值：1.进行中 2. 已结束
    @Column(name = "C_DESCRIPTION")
    private String description; // 宣传内容(活动描述信息)


    public String getTitleImg() {
        //默认是从/bos_management开始
        if (titleImg.startsWith("http:")) {
            return titleImg;
        }
        return Constant.BOS_MANAGEMENT_URL + titleImg;
    }

    public String getDescription() {
        if (description==null){
            return "";
        }
        else if (description.contains(Constant.BOS_MANAGEMENT_URL + "/bos_management")) {
            return description;
        }
        return description.replace("src=\"/bos_management", "src=\"/bos_management" + Constant.BOS_MANAGEMENT_URL + "/bos_management");
    }

}
