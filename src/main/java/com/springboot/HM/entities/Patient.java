package com.springboot.HM.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private Date Created_DateTime;
	private Date Modified_DateTime;
	private String Age;
	private String Dob;
	private String FirstName;
	private String LastName;
	private String Gender;
	private Long Phone_Number;
	private String Address;
	private String City;
	@Column(name = "Email", unique = true)
	private String Email_Id;

	public Patient(Long id, Date created_DateTime, Date modified_DateTime, String age, String dob, String firstName,
			String lastName, String gender, Long phone_Number, String address, String city, String email_Id) {
		Id = id;
		Created_DateTime = created_DateTime;
		Modified_DateTime = modified_DateTime;
		Age = age;
		Dob = dob;
		FirstName = firstName;
		LastName = lastName;
		Gender = gender;
		Phone_Number = phone_Number;
		Address = address;
		City = city;
		Email_Id = email_Id;
	}

	public Patient() {

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
	 * @return the age
	 */
	public String getAge() {
		return Age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		Age = age;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return Dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		Dob = dob;
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
	 * @return the gender
	 */
	public String getGender() {
		return Gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
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
	 * @return the address
	 */
	public String getAddress() {
		return Address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		Address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		City = city;
	}

	/**
	 * @return the email_Id
	 */
	public String getEmail_Id() {
		return Email_Id;
	}

	/**
	 * @param email_Id the email_Id to set
	 */
	public void setEmail_Id(String email_Id) {
		Email_Id = email_Id;
	}

}
