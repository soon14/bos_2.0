package io.maang.bos.domain.base;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:收派时间管理
 */
@Entity
@Table(name = "T_TAKE_TIME")
@Data
public class TakeTime {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id; // 主键
	@Column(name = "C_NAME")
	private String name; // 收派时间名称
	@Column(name = "C_NORMAL_WORK_TIME")
	private String normalWorkTime; // 平常上班时间
	@Column(name = "C_NORMAL_DUTY_TIME")
	private String normalDutyTime; // 平常下班时间
	@Column(name = "C_SAT_WORK_TIME")
	private String satWorkTime; // 周六上班时间
	@Column(name = "C_SAT_DUTY_TIME")
	private String satDutyTime; // 周六下班时间
	@Column(name = "C_SUN_WORK_TIME")
	private String sunWorkTime; // 周日上班时间
	@Column(name = "C_SUN_DUTY_TIME")
	private String sunDutyTime; // 周日下班时间
	@Column(name = "C_STATUS")
	private String status; // 状态
	@Column(name = "C_COMPANY")
	private String company; // 所属公司

	@Column(name = "C_OPERATING_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;// 操作时间
	@Column(name = "C_OPERATOR")
	private String operator; // 操作员
	@Column(name = "C_OPERATING_COMPANY")
	private String operatingCompany; // 操作单位


}
