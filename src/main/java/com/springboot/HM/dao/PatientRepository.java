package com.springboot.HM.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.HM.entities.Patient;
import com.springboot.HM.entities.User;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	@Query(value = "select * from patient GROUP BY id", nativeQuery = true)
	public List<Patient> getEmails();

	@Query(value = "select * from patient", nativeQuery = true)
	public Page<Patient> getAllPatient(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "delete from patient where id IN (:id)", nativeQuery = true)
	public void DeleteAllById(@Param("id") Long[] id);

	@Query(value = "select * from patient p where CONCAT(p.last_name like %:lastname% OR p.city like %:city%)", nativeQuery = true)
	public List<Patient> Search(@Param("lastname") String lastname, @Param("city") String city);

}
