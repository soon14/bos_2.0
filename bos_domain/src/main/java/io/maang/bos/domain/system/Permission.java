package io.maang.bos.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:权限名称
 */
@Entity
@Table(name = "T_PERMISSION")
@Getter
@Setter
public class Permission {

	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private int id;
	@Column(name = "C_NAME")
	private String name; // 权限名称
	@Column(name = "C_KEYWORD")
	private String keyword; // 权限关键字，用于权限控制
	@Column(name = "C_DESCRIPTION")
	private String description; // 描述

	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles = new HashSet<Role>(0);


}
