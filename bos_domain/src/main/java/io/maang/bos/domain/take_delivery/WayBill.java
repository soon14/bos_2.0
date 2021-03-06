package io.maang.bos.domain.take_delivery;

import io.maang.bos.domain.base.Area;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

/**
 * @description:运单实体类
 */
@Entity
@Table(name = "T_WAY_BILL")
@Data
@Document(indexName = "bos",type = "waybill")
public class WayBill {

	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	@org.springframework.data.annotation.Id
	@Field(index = FieldIndex.not_analyzed,store = true,type = FieldType.Integer)
	private Integer id;

	@Column(name = "C_WAY_BILL_NUM", unique = true)
	@Field(index = FieldIndex.not_analyzed,store = true,type = FieldType.String)
	private String wayBillNum; // 运单编号

	@OneToOne
	@JoinColumn(name = "C_ORDER_ID")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private Order order; // 订单信息

	@Column(name = "C_SEND_NAME")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private String sendName; // 寄件人姓名

	@Column(name = "C_SEND_MOBILE")
	private String sendMobile;// 寄件人电话

	@Column(name = "C_SEND_COMPANY")
	private String sendCompany;// 寄件人公司

	@OneToOne
	@JoinColumn(name = "C_SEND_AREA_ID")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private Area sendArea; // 寄件人省市区信息

	@Column(name = "C_SEND_ADDRESS")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private String sendAddress;// 寄件人详细地址信息

	@Column(name = "C_REC_NAME")
	private String recName;// 收件人姓名

	@Column(name = "C_REC_MOBILE")
	private String recMobile;// 收件人电话

	@Column(name = "C_REC_COMPANY")
	private String recCompany;// 收件人公司

	@OneToOne
	@JoinColumn(name = "C_REC_AREA_ID")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private Area recArea; // 收件人省市区信息

	@Column(name = "C_REC_ADDRESS")
	@Field(index = FieldIndex.analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private String recAddress;// 收件人详细地址信息

	@Column(name = "C_SEND_PRO_NUM")
	@Field(index = FieldIndex.not_analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private String sendProNum; // 快递产品类型编号：速运当日、速运次日、速运隔日

	@Column(name = "C_GOODS_TYPE")
	private String goodsType;// 托寄物类型：文件、衣服 、食品、电子商品

	@Column(name = "C_PAY_TYPE_NUM")
	private String payTypeNum;// 支付类型编号：寄付日结、寄付月结、到付

	@Column(name = "C_WEIGHT")
	private Double weight;// 托寄物重量

	@Column(name = "C_REMARK")
	private String remark; // 备注

	@Column(name = "C_NUM")
	private Integer num; // 原件数

	@Column(name = "C_ARRIVE_CITY")
	private String arriveCity; // 到达地

	@Column(name = "C_FEEITEMNUM")
	private Integer feeitemnum; // 实际件数

	@Column(name = "C_ACTLWEIT")
	private Double actlweit; // 实际重量

	@Column(name = "C_VOL")
	private String vol; // 体积 输入格式为1*1*1*1，表示长*宽*高*数量

	@Column(name = "C_FLOADREQR")
	private String floadreqr; // 配载要求 (比如录入1=无，2=禁航，4=禁航空铁路)

	@Column(name = "C_WAY_BILL_TYPE")
	private String wayBillType; // 运单类型（类型包括：正常单据、异单、调单）
	/*
	 * 运单状态： 1 待发货、 2 派送中、3 已签收、4 异常
	 */
	@Column(name = "C_SIGN_STATUS")
	@Field(index = FieldIndex.not_analyzed,analyzer = "ik",store = true,type = FieldType.String,searchAnalyzer = "ik")
	private Integer signStatus; // 签收状态

	/*
	 * 1、新增修改单据状态为“否” 2、作废时需将状态置为“是” 3、取消作废时需要将状态置为“否”
	 */
	@Column(name = "C_DELTAG")
	private String delTag; // 作废标志



}
