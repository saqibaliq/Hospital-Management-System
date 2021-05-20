package com.springboot.HM.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Date Created_DateTime;
	private Date Modified_DateTime;
	private String RoleDesc;
	private String RoleName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<User> user;

	public Role(Long id, String roleDesc, String roleName, List<User> user) {

		Id = id;
		RoleDesc = roleDesc;
		RoleName = roleName;
		this.user = user;
	}

	public Role() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}

	/**
	 * @return the created_DateTime
	 */
	public Date getCreated_DateTime() {
		return Created_DateTime;
	}

	/**
	 * @param created_DateTime the created_DateTime to set
	 */
	public void setCreated_DateTime(Date created_DateTime) {
		Created_DateTime = new Date();
	}

	/**
	 * @return the modified_DateTime
	 */
	public Date getModified_DateTime() {
		return Modified_DateTime;
	}

	/**
	 * @param modified_DateTime the modified_DateTime to set
	 */
	public void setModified_DateTime(Date modified_DateTime) {
		Modified_DateTime = new Date();
	}

	/**
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return RoleDesc;
	}

	/**
	 * @param roleDesc the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		RoleDesc = roleDesc;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return RoleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	/**
	 * @return the user
	 */
	public List<User> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(List<User> user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [Id=" + Id + ", Created_DateTime=" + Created_DateTime + ", Modified_DateTime=" + Modified_DateTime
				+ ", RoleDesc=" + RoleDesc + ", RoleName=" + RoleName + ", user=" + user + "]";
	}

}
