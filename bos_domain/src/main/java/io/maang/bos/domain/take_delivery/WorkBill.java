package io.maang.bos.domain.take_delivery;


import io.maang.bos.domain.base.Courier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:工单
 */
@Entity
@Table(name = "T_WORK_BILL")
@Setter@Getter
public class WorkBill {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id; // 主键
	@Column(name = "C_TYPE")
	private String type; // 工单类型 新,追,销
	/*
	 * 新单:没有确认货物状态的 已通知:自动下单下发短信 已确认:接到短信,回复收信确认信息 已取件:已经取件成功,发回确认信息 生成工作单
	 * 已取消:销单
	 *
	 */
	@Column(name = "C_PICKSTATE")
	private String pickstate; // 取件状态
	@Column(name = "C_BUILDTIME")
	private Date buildtime; // 工单生成时间
	@Column(name = "C_ATTACHBILLTIMES")
	private Integer attachbilltimes; // 追单次数
	@Column(name = "C_REMARK")
	private String remark; // 订单备注
	@Column(name = "C_SMSNUMBER")
	private String smsNumber; // 短信序号

	@OneToOne
	@JoinColumn(name = "C_COURIER")
	private Courier courier;// 快递员

	@ManyToOne
	@JoinColumn(name = "C_ORDER_ID")
	private Order order; // 订单



}
