package com.springboot.HM.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.HM.dao.AppointmentRepository;
import com.springboot.HM.dao.PatientRepository;
import com.springboot.HM.dao.RoleRepository;
import com.springboot.HM.dao.UserRepository;
import com.springboot.HM.entities.Appointment;
import com.springboot.HM.entities.Patient;
import com.springboot.HM.entities.Role;
import com.springboot.HM.entities.User;
import com.springboot.HM.helper.Message;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private UserRepository userRepositiry;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/patient_list/{page}")
	public String patientList(@PathVariable("page") Integer page, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);

		Pageable pageable = PageRequest.of(page, 2);
		Page<Patient> allPatient = this.patientRepository.getAllPatient(pageable);
		model.addAttribute("patient", allPatient);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allPatient.getTotalPages());
		return "doctor/PatientList";
	}

	@GetMapping("/appointment_list/{page}")
	public String appointmentList(@PathVariable("page") Integer page, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);

		Pageable pageable = PageRequest.of(page, 2);
		Page<Appointment> allAppointments = this.appointmentRepository.getAllAppointments(pageable);
		model.addAttribute("appointment", allAppointments);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allAppointments.getTotalPages());
		return "doctor/AppointmentList";
	}

	@GetMapping("/profile")
	public String openProfileDoctor(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		User userByUserName = this.userRepositiry.getUserByUserName(username);
		model.addAttribute("user", userByUserName);
		httpSession.setAttribute("PID", userByUserName.getId());
		System.out.println("PID " + userByUserName.getId());
		return "doctor/Profile";
	}

	@PostMapping("/updateProfile")
	public String ProfileUpdateDoctor(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			Long PID = (Long) httpSession.getAttribute("PID");
			System.out.println("PID " + PID);
			Role roleByName = this.roleRepository.getRoleByName("Doctor");
			System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setRole(roleByName);
			System.out.println("USER " + user.getId());
			user.setId(PID);
			User save = this.userRepositiry.save(user);
			model.addAttribute("user", save);
			model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Profile Updated Successfully.. ", "alert-success"));
			return "doctor/Profile";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "doctor/Profile";
		}

	}

}
