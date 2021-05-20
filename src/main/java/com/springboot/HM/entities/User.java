package com.springboot.HM.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date Created_DateTime;
	private Date Modified_DateTime;
	private String qualification;
	private String Address;
	private String Age;
	private String BloodGroup;
	private String City;
	private String Dob;
	@Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	@Column(name = "Email", unique = true)
	private String Email;
	private String FirstName;
	private String LastName;
	private String Gender;
	private String Join_Date;
	private String Martial_Status;
	private String Password;
	private Long Phone_Number;
	private String Specialization;
	@Column(name = "User_Name", unique = true)
	private String UserName;
	@ManyToOne
	@JoinColumn(name = "role_id")
	@JsonIgnore
	private Role role;

	public User(Long Id, String Qualification, String address, String age, String bloodGroup, String city, String dob,
			String email, String firstName, String lastName, String gender, String join_Date, String martial_Status,
			String password, Long phone_Number, String specialization, String userName, Role role) {
		id = Id;
		qualification = Qualification;
		Address = address;
		Age = age;
		BloodGroup = bloodGroup;
		City = city;
		Dob = dob;
		Email = email;
		FirstName = firstName;
		LastName = lastName;
		Gender = gender;
		Join_Date = join_Date;
		Martial_Status = martial_Status;
		Password = password;
		Phone_Number = phone_Number;
		Specialization = specialization;
		UserName = userName;
		this.role = role;
	}

	public User() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * @param qualification the qualification to set
	 */
	public void setQualification(String Qualification) {
		qualification = Qualification;
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
	 * @return the bloodGroup
	 */
	public String getBloodGroup() {
		return BloodGroup;
	}

	/**
	 * @param bloodGroup the bloodGroup to set
	 */
	public void setBloodGroup(String bloodGroup) {
		BloodGroup = bloodGroup;
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
	 * @return the join_Date
	 */
	public String getJoin_Date() {
		return Join_Date;
	}

	/**
	 * @param join_Date the join_Date to set
	 */
	public void setJoin_Date(String join_Date) {
		Join_Date = join_Date;
	}

	/**
	 * @return the martial_Status
	 */
	public String getMartial_Status() {
		return Martial_Status;
	}

	/**
	 * @param martial_Status the martial_Status to set
	 */
	public void setMartial_Status(String martial_Status) {
		Martial_Status = martial_Status;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return the roll_Id
	 */

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
	 * @return the specialization
	 */
	public String getSpecialization() {
		return Specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	 * @Override public String toString() { return "User [id=" + id +
	 * ", Created_DateTime=" + Created_DateTime + ", Modified_DateTime=" +
	 * Modified_DateTime + ", Qualification=" + qualification + ", Address=" +
	 * Address + ", Age=" + Age + ", BloodGroup=" + BloodGroup + ", City=" + City +
	 * ", Dob=" + Dob + ", Email=" + Email + ", FirstName=" + FirstName +
	 * ", LastName=" + lastName + ", Gender=" + Gender + ", Join_Date=" + Join_Date
	 * + ", Martial_Status=" + Martial_Status + ", Password=" + Password +
	 * ", Phone_Number=" + Phone_Number + ", Specialization=" + Specialization +
	 * ", UserName=" + UserName + ", role=" + role + "]"; }
	 */

}
