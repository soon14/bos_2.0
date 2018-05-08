package io.maang.bos.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:后台用户
 */
@Entity
@Table(name = "T_USER")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id; // 主键
	@Column(name = "C_BIRTHDAY")
	private Date birthday; // 生日
	@Column(name = "C_GENDER")
	private String gender; // 性别
	@Column(name = "C_PASSWORD")
	private String password; // 密码
	@Column(name = "C_REMARK")
	private String remark; // 备注
	@Column(name = "C_STATION")
	private String station; // 状态
	@Column(name = "C_TELEPHONE")
	private String telephone; // 联系电话
	@Column(name = "C_USERNAME", unique = true)
	private String username; // 登陆用户名
	@Column(name = "C_NICKNAME")
	private String nickname; // 真实姓名

	@ManyToMany
	@JoinTable(name = "T_USER_ROLE", joinColumns = {
			@JoinColumn(name = "C_USER_ID", referencedColumnName = "C_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "C_ROLE_ID", referencedColumnName = "C_ID") })
	private Set<Role> roles = new HashSet<Role>(0);


}
