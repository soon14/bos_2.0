package io.maang.bos.domain.base;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @description:地域信息实体类，主要包含 省市区(县)
 */
@Entity
@Table(name = "T_AREA")
@Data
public class Area {

	@Id
	@Column(name = "C_ID")
	private String id;
	@Column(name = "C_PROVINCE")
	private String province; // 省
	@Column(name = "C_CITY")
	private String city; // 城市
	@Column(name = "C_DISTRICT")
	private String district; // 区域
	@Column(name = "C_POSTCODE")
	private String postcode; // 邮编
	@Column(name = "C_CITYCODE")
	private String citycode; // 城市编码
	@Column(name = "C_SHORTCODE")
	private String shortcode; // 简码

	@OneToMany(mappedBy = "area")
	private Set<SubArea> subareas = new HashSet<SubArea>();



}
