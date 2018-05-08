package io.maang.bos.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @description:分区
 */
@Entity
@Table(name = "T_SUB_AREA")
@Getter@Setter
public class SubArea {

	@Id
	@Column(name = "C_ID")
	private String id;
	@Column(name = "C_START_NUM")
	private String startNum; // 起始号
	@Column(name = "C_ENDNUM")
	private String endNum; // 终止号
	@Column(name = "C_SINGLE")
	private Character single; // 单双号
	@Column(name = "C_KEY_WORDS")
	private String keyWords; // 关键字
	@Column(name = "C_ASSIST_KEY_WORDS")
	private String assistKeyWords; // 辅助关键字

	@ManyToOne
	@JoinColumn(name = "C_AREA_ID")
	private Area area; // 区域
	@ManyToOne
	@JoinColumn(name = "C_FIXEDAREA_ID")
	private FixedArea fixedArea; // 定区


}
