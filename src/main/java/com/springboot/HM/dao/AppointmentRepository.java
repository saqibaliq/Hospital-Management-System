package com.springboot.HM.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.HM.entities.Appointment;
import com.springboot.HM.entities.Patient;
import com.springboot.HM.entities.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	@Query(value = "select * from appointment", nativeQuery = true)
	public Page<Appointment> getAllAppointments(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "delete from appointment where id IN (:id)", nativeQuery = true)
	public void DeleteAllById(@Param("id") Long[] id);

	@Query(value = "select * from appointment u where CONCAT(u.firstname_name like %:lastname% OR u.last_name like %:firstname%)", nativeQuery = true)
	public List<Appointment> Search(@Param("firstname") String firstname, @Param("lastname") String lastname);

}
