package io.maang.bos.domain.base;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:子档案类，记录了档案分级后的子信息
 */
@Entity
@Table(name = "T_SUB_ARCHIVE")
@Data
public class SubArchive {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id; // 主键
	@Column(name = "C_SUB_ARCHIVE_NAME")
	private String subArchiveName; // 子档名称
	@Column(name = "C_MNEMONIC_CODE")
	private String mnemonicCode; // 助记码
	@Column(name = "C_REMARK")
	private String remark; // 备注
	@Column(name = "C_MOTHBALLED")
	private Character mothballed; // 封存标志
	@Column(name = "C_OPERATING_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;// 操作时间
	@Column(name = "C_OPERATOR")
	private String operator; // 操作员
	@Column(name = "C_OPERATING_COMPANY")
	private String operatingCompany; // 操作单位

	@ManyToOne
	@JoinColumn(name = "C_ARCHIVE_ID")
	private Archive archive; // 关联基本档案信息


}
