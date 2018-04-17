package io.maang.bos.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:定区
 */
@Entity
@Table(name = "T_FIXED_AREA")
@Setter@Getter
public class FixedArea {

	@Id
	@Column(name = "C_ID")
	private String id; // 主键
	@Column(name = "C_FIXED_AREA_NAME", unique = true)
	private String fixedAreaName; // 定区名称
	@Column(name = "C_FIXED_AREA_LEADER", unique = true)
	private String fixedAreaLeader;// 定区负责人
	@Column(name = "C_TELEPHONE")
	private String telephone;// 联系电话
	@Column(name = "C_COMPANY")
	private String company; // 所属单位

	@Column(name = "C_OPERATING_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;// 操作时间
	@Column(name = "C_OPERATOR")
	private String operator; // 操作员
	@Column(name = "C_OPERATING_COMPANY")
	private String operatingCompany; // 操作单位

	@OneToMany(mappedBy = "fixedArea")
	private Set<SubArea> subareas = new HashSet<SubArea>(0);

	@ManyToMany
	@JoinTable(name = "T_FIXEDAREA_COURIER", joinColumns = { @JoinColumn(name = "C_FIXED_AREA_ID", referencedColumnName = "C_ID") }, inverseJoinColumns = { @JoinColumn(name = "C_COURIER_ID", referencedColumnName = "C_ID") })
	private Set<Courier> couriers = new HashSet<Courier>(0);


}
