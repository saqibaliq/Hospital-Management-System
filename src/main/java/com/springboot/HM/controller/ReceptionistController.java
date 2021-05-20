package com.springboot.HM.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.HM.dao.PatientRepository;
import com.springboot.HM.dao.RoleRepository;
import com.springboot.HM.dao.UserRepository;
import com.springboot.HM.entities.Patient;
import com.springboot.HM.entities.Role;
import com.springboot.HM.entities.User;
import com.springboot.HM.helper.Message;
import com.springboot.HM.services.EmailService;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EmailService emailService;

	@GetMapping("/add_patient")
	public String openPatientForm(Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		return "receptionist/AddPatient";
	}

	@PostMapping("/addPatient")
	public String SavePatient(@ModelAttribute("patient") Patient patient, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			patient.setCreated_DateTime(new Date());
			patient.setModified_DateTime(new Date());
			// user.setRole(roleByName);
			List<Patient> emails = this.patientRepository.getEmails();
			for (Patient elements : emails) {
				if (elements.getEmail_Id().equalsIgnoreCase(patient.getEmail_Id())) {
					httpSession.setAttribute("emailmessage",
							new Message("Email already registered . Try with Another", "alert-warning"));
					return "receptionist/AddPatient";
				}
			}

			Patient patient2 = this.patientRepository.save(patient);
			model.addAttribute("patient", patient2);
			// model.addAttribute("name", patient.getFirstName());
			httpSession.setAttribute("message", new Message("Patient Added Successfully.. ", "alert-success"));
			return "receptionist/AddPatient";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", patient);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "receptionist/AddPatient";
		}

	}

	@GetMapping("/patient_list/{page}")
	public String patientList(@PathVariable("page") Integer page, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);

		Pageable pageable = PageRequest.of(page, 2);
		Page<Patient> allPatient = this.patientRepository.getAllPatient(pageable);
		model.addAttribute("patient", allPatient);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allPatient.getTotalPages());
		return "receptionist/PatientList";
	}

	@GetMapping("/update_patient/{Id}")
	public String openUpdatePatient(@PathVariable("Id") Long Id, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		httpSession.setAttribute("PId", Id);
		Optional<Patient> findById = this.patientRepository.findById(Id);
		Patient patient = findById.get();
		model.addAttribute("patient", patient);
		return "receptionist/UpdatePatient";
	}

	@PostMapping("/updatePatient")
	public String UpdatePatient(@ModelAttribute("patient") Patient patient, Model model, HttpSession httpSession) {
		try {
			Long PId = (Long) httpSession.getAttribute("PId");
			patient.setCreated_DateTime(new Date());
			patient.setModified_DateTime(new Date());
			patient.setId(PId);
			Patient patient2 = this.patientRepository.save(patient);

			model.addAttribute("patient", patient2);
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			httpSession.setAttribute("message", new Message("patient Updated Successfully.. ", "alert-success"));
			return "receptionist/UpdatePatient";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("patient", patient);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "receptionist/UpdatePatient";
		}

	}

	@RequestMapping(value = "/deletePatient/{page}", method = RequestMethod.POST)
	public String DeletePatient(@PathVariable("page") Integer page,
			@RequestParam(value = "cb", required = false) Long[] cb, HttpSession httpSession, Model model) {

		if (cb == null) {
			httpSession.setAttribute("error",
					new Message("No Patient Data To be Deleted OR You Have not Seleted Box", "alert-danger"));

		}

		Pageable pageable = PageRequest.of(page, 2);
		Page<Patient> allPatient = this.patientRepository.getAllPatient(pageable);

		model.addAttribute("patient", allPatient);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allPatient.getTotalPages());
		this.patientRepository.DeleteAllById(cb);

		return "redirect:/receptionist/patient_list/{page}";
	}

	@GetMapping("/profile")
	public String openProfileDoctor(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		User userByUserName = this.userRepository.getUserByUserName(username);
		model.addAttribute("user", userByUserName);
		httpSession.setAttribute("PID", userByUserName.getId());
		System.out.println("PID " + userByUserName.getId());
		return "receptionist/Profile";
	}

	@PostMapping("/updateProfile")
	public String ProfileUpdateDoctor(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			Long PID = (Long) httpSession.getAttribute("PID");
			System.out.println("PID " + PID);
			Role roleByName = this.roleRepository.getRoleByName("Receptionist");
			System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setRole(roleByName);
			System.out.println("USER " + user.getId());
			user.setId(PID);
			User save = this.userRepository.save(user);
			model.addAttribute("user", save);
			model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Profile Updated Successfully.. ", "alert-success"));
			return "receptionist/Profile";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "receptionist/Profile";
		}

	}
}
