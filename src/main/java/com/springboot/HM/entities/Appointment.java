package com.springboot.HM.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private Date Created_DateTime;
	private Date Modified_DateTime;
	private String Appointment_Date;
	private String Doctor_Name;
	private String FirstName;
	private String LastName;
	private String Email;
	private Long Phone_Number;
	private String Appointment_Time;

	public Appointment(Long id, Date created_DateTime, Date modified_DateTime, String appointment_Date,
			String doctor_Name, String firstName, String lastName, String email, Long phone_Number,
			String appointment_Time) {

		Id = id;
		Created_DateTime = created_DateTime;
		Modified_DateTime = modified_DateTime;
		Appointment_Date = appointment_Date;
		Doctor_Name = doctor_Name;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		Phone_Number = phone_Number;
		Appointment_Time = appointment_Time;
	}

	public Appointment() {
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
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
		Created_DateTime = created_DateTime;
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
		Modified_DateTime = modified_DateTime;
	}

	/**
	 * @return the appointment_Date
	 */
	public String getAppointment_Date() {
		return Appointment_Date;
	}

	/**
	 * @param appointment_Date the appointment_Date to set
	 */
	public void setAppointment_Date(String appointment_Date) {
		Appointment_Date = appointment_Date;
	}

	/**
	 * @return the doctor_Name
	 */
	public String getDoctor_Name() {
		return Doctor_Name;
	}

	/**
	 * @param doctor_Name the doctor_Name to set
	 */
	public void setDoctor_Name(String doctor_Name) {
		Doctor_Name = doctor_Name;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}

	/**
	 * @return the phone_Number
	 */
	public Long getPhone_Number() {
		return Phone_Number;
	}

	/**
	 * @param phone_Number the phone_Number to set
	 */
	public void setPhone_Number(Long phone_Number) {
		Phone_Number = phone_Number;
	}

	/**
	 * @return the appointment_Time
	 */
	public String getAppointment_Time() {
		return Appointment_Time;
	}

	/**
	 * @param appointment_Time the appointment_Time to set
	 */
	public void setAppointment_Time(String appointment_Time) {
		Appointment_Time = appointment_Time;
	}

}
