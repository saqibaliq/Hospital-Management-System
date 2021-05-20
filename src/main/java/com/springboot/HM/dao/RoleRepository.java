package com.springboot.HM.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.HM.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "select * from role where role_name = :role_name", nativeQuery = true)
	public Role getRoleByName(@Param("role_name") String role_name);
}
