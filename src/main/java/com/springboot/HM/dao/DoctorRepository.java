package com.springboot.HM.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.HM.entities.User;

public interface DoctorRepository extends JpaRepository<User, Long> {

}
