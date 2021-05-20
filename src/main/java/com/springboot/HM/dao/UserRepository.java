package com.springboot.HM.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.HM.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "select * from user u where u.user_name = :user_name and u.password = :password", nativeQuery = true)
	public User Login(@Param("user_name") String user_name, @Param("password") String password);

	@Query(value = "select * from user where role_id = 2", nativeQuery = true)
	public Page<User> getAllDoctorsById(Pageable pageable);

	@Query(value = "select * from user where role_id = 3", nativeQuery = true)
	public Page<User> getAllReceptionistById(Pageable pageable);

	@Query(value = "SELECT * FROM user where role_id = 2 GROUP BY qualification", nativeQuery = true)
	public List<User> getUniqueQualification();

	@Query(value = "select * from user GROUP BY id", nativeQuery = true)
	public List<User> getEmails();

	/*
	 * @Query(value =
	 * "select * from user where last_name like %:lastname% and role_id = 2",
	 * nativeQuery = true) public List<User> getSearchByLastName(@Param("lastname")
	 * String lastname);
	 * 
	 * @Query(value =
	 * "select * from user where qualification like %:qualification% and role_id = 2"
	 * , nativeQuery = true) public List<User>
	 * getSearchByQualification(@Param("qualification") String qualification);
	 */
	@Query(value = "select * from user u where CONCAT(u.last_name like %:lastname% OR u.qualification like %:qualification%) and role_id = 2", nativeQuery = true)
	public List<User> Search(@Param("lastname") String lastname, @Param("qualification") String qualification);

	@Query(value = "select * from user u where CONCAT(u.last_name like %:lastname% OR u.email like %:email%) and role_id = 3", nativeQuery = true)
	public List<User> SearchRecep(@Param("lastname") String lastname, @Param("email") String email);
	/*
	 * @Query(value =
	 * "select * from user where last_name like %:lastname% and role_id = 3",
	 * nativeQuery = true) public List<User> getSearchByLastName(@Param("lastname")
	 * String lastname);
	 */

	// List<User> findByLastNameContainingOrQualificationContaining(String lastname,
	// String qualification);

	@Query(value = "select * from user where role_id = 2", nativeQuery = true)
	public List<User> getAllDoctors();

	@Transactional
	@Modifying
	@Query(value = "delete from user where role_id = 2 And id IN (:id)", nativeQuery = true)
	public void DeleteAllById(@Param("id") Long[] id);

	@Transactional
	@Modifying
	@Query(value = "delete from user where role_id = 3 And id IN (:ids)", nativeQuery = true)
	public void BulkDeleteReceptionistById(@Param("ids") Long[] id);

	@Query(value = "select * from user where user_name = :user_name", nativeQuery = true)
	public User getUserByUserName(@Param("user_name") String user_name);
}
