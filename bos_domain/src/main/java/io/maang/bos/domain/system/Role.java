package io.maang.bos.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:角色
 */
@Entity
@Table(name = "T_ROLE")
@Getter
@Setter
public class Role {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private int id;
	@Column(name = "C_NAME")
	private String name; // 角色名称
	@Column(name = "C_KEYWORD")
	private String keyword; // 角色关键字，用于权限控制
	@Column(name = "C_DESCRIPTION")
	private String description; // 描述

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<User>(0);

	@ManyToMany
	@JoinTable(name = "T_ROLE_PERMISSION", joinColumns = {
			@JoinColumn(name = "C_ROLE_ID", referencedColumnName = "C_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "C_PERMISSION_ID", referencedColumnName = "C_ID") })
	private Set<Permission> permissions = new HashSet<Permission>(0);

	@ManyToMany
	@JoinTable(name = "T_ROLE_MENU", joinColumns = {
			@JoinColumn(name = "C_ROLE_ID", referencedColumnName = "C_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "C_MENU_ID", referencedColumnName = "C_ID") })
	private Set<Menu> menus = new HashSet<Menu>(0);



}
