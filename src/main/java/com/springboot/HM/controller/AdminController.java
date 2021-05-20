package com.springboot.HM.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.HM.dao.AppointmentRepository;
import com.springboot.HM.dao.PatientRepository;
import com.springboot.HM.dao.RoleRepository;
import com.springboot.HM.dao.UserRepository;
import com.springboot.HM.entities.Appointment;
import com.springboot.HM.entities.Patient;
import com.springboot.HM.entities.Role;
import com.springboot.HM.entities.User;
import com.springboot.HM.helper.Message;
import com.springboot.HM.services.EmailService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private EmailService emailService;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

//Doctor Methods And Controllers Start
	@GetMapping("/add_doctor")
	public String openDoctorForm(Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		return "admin/AddDoctor";
	}

	@GetMapping("/doctor_list/{page}")
	public String doctorList(@PathVariable("page") Integer page, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		List<User> uniqueQualification = this.userRepository.getUniqueQualification();
		model.addAttribute("uniqueQual", uniqueQualification);
		Pageable pageable = PageRequest.of(page, 2);
		Page<User> allDoctorsById = this.userRepository.getAllDoctorsById(pageable);
		model.addAttribute("doctor", allDoctorsById);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allDoctorsById.getTotalPages());
		return "admin/DoctorList";
	}

	@GetMapping("/update_doctor/{Id}")
	public String openUpdateDoctor(@PathVariable("Id") Long Id, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		httpSession.setAttribute("DId", Id);
		Optional<User> findById = this.userRepository.findById(Id);
		User user = findById.get();
		model.addAttribute("user", user);
		return "admin/UpdateDoctor";
	}

	@GetMapping("/profile")
	public String openProfile(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		User userByUserName = this.userRepository.getUserByUserName(username);
		model.addAttribute("user", userByUserName);
		httpSession.setAttribute("PID", userByUserName.getId());
		System.out.println("PID " + userByUserName.getId());
		return "admin/Profile";
	}

	@PostMapping("/updateProfile")
	public String ProfileUpdate(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			Long PID = (Long) httpSession.getAttribute("PID");
			System.out.println("PID " + PID);
			Role roleByName = this.roleRepository.getRoleByName("Admin");
			System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setRole(roleByName);
			// System.out.println("USER " + user.getId());
			user.setId(PID);
			User user2 = this.userRepository.save(user);

			model.addAttribute("user", user2);
			model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Profile Updated Successfully.. ", "alert-success"));
			return "admin/Profile";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/Profile";
		}

	}

	@PostMapping("/updateDoctor")
	public String UpdateDoc(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			Long DId = (Long) httpSession.getAttribute("DId");
			System.out.println("DID " + DId);
			Role roleByName = this.roleRepository.getRoleByName("Doctor");
			// System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setRole(roleByName);
			// System.out.println("USER " + user.getId());
			user.setId(DId);
			User user2 = this.userRepository.save(user);

			model.addAttribute("user", user2);
			model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Doctor Updated Successfully.. ", "alert-success"));
			return "admin/UpdateDoctor";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/UpdateDoctor";
		}

	}

	@PostMapping("/addDoctor")
	public String SaveDoc(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			Role roleByName = this.roleRepository.getRoleByName("Doctor");
			// System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setPassword(user.getPassword());
			user.setRole(roleByName);
			List<User> emails = this.userRepository.getEmails();
			for (User elements : emails) {
				if (elements.getEmail().equalsIgnoreCase(user.getEmail())) {
					httpSession.setAttribute("emailmessage",
							new Message("Email already registered . Try with Another", "alert-warning"));
					return "admin/AddDoctor";
				}
			}
			User user2 = this.userRepository.save(user);

			model.addAttribute("user", user2);
			// model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Doctor Added Successfully.. ", "alert-success"));
			return "admin/AddDoctor";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/AddDoctor";
		}

	}

	@RequestMapping(value = "/search_doctor/{page}", method = RequestMethod.POST)
	public String Search(@PathVariable("page") Integer page, @Param("lastname") String lastname,
			@Param("qual") String qual, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);

			Pageable pageable = PageRequest.of(page, 2);
			Page<User> allDoctorsById = this.userRepository.getAllDoctorsById(pageable);
			model.addAttribute("doctor", allDoctorsById);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", allDoctorsById.getTotalPages());
			List<User> uniqueQualification = this.userRepository.getUniqueQualification();
			model.addAttribute("uniqueQual", uniqueQualification);
			/*
			 * List<User> searchByLastName =
			 * this.userRepository.getSearchByLastName(lastname); List<User>
			 * searchByQualification = this.userRepository.getSearchByQualification(qual);
			 */
			// List<User> search = this.userRepository.Search(lastname, qual);
			List<User> search = this.userRepository.Search(lastname, qual);
			model.addAttribute("doctor", search);
			// model.addAttribute("doctor", searchByQualification);

			return "admin/DoctorList";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "admin/DoctorList";
	}

	@RequestMapping(value = "/deleteDoc/{page}", method = RequestMethod.POST)
	public String DeleteDoc(@PathVariable("page") Integer page, @RequestParam(value = "cb", required = false) Long[] cb,
			HttpSession httpSession, Model model) {

		if (cb == null) {
			httpSession.setAttribute("error",
					new Message("No Doctor Data To be Deleted OR You Have not Seleted Box", "alert-danger"));

		}

		Pageable pageable = PageRequest.of(page, 2);
		Page<User> allDoctorsById = this.userRepository.getAllDoctorsById(pageable);

		model.addAttribute("doctor", allDoctorsById);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allDoctorsById.getTotalPages());
		List<User> uniqueQualification = this.userRepository.getUniqueQualification();
		model.addAttribute("uniqueQual", uniqueQualification);
		// String ids = StringUtil.toCommaSeparatedString(cb);
		System.out.println("Delete Done");
		this.userRepository.DeleteAllById(cb);
		// String[] split = ids.split(",");
		/*
		 * for (int i = 0; i < split.length; i++) { System.out.println(split[i]);
		 * this.userRepository.DeleteAllById(split[i]); }
		 */

		return "redirect:/admin/doctor_list/{page}";
	}
	// Doctor Methods And Controllers End

	// Receptionist Methods And Controllers Start

	@GetMapping("/add_receptionist")
	public String openReceptionistForm(Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		return "admin/AddReceptionist";
	}

	@PostMapping("/addReceptionist")
	public String SaveReceptionist(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			Role roleByName = this.roleRepository.getRoleByName("Receptionist");
			// System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setPassword(user.getPassword());
			user.setRole(roleByName);
			List<User> emails = this.userRepository.getEmails();
			for (User elements : emails) {
				if (elements.getEmail().equalsIgnoreCase(user.getEmail())) {
					httpSession.setAttribute("emailmessage",
							new Message("Email already registered . Try with Another", "alert-warning"));
					return "admin/AddReceptionist";
				}
			}
			User user2 = this.userRepository.save(user);

			model.addAttribute("user", user2);
			// model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Receptionist Added Successfully.. ", "alert-success"));
			return "admin/AddReceptionist";
		} catch (Exception ex) {
			ex.printStackTrace();

			model.addAttribute("user", user);

			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/Receptionist";
		}

	}

	@GetMapping("/receptionist_list/{page}")
	public String receptionistList(@PathVariable("page") Integer page, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		// List<User> uniqueQualification =
		// this.userRepository.getUniqueQualification();
		// model.addAttribute("uniqueQual", uniqueQualification);
		Pageable pageable = PageRequest.of(page, 2);
		Page<User> allDoctorsById = this.userRepository.getAllReceptionistById(pageable);
		model.addAttribute("receptionist", allDoctorsById);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allDoctorsById.getTotalPages());
		return "admin/ReceptionistList";
	}

	@GetMapping("/update_receptionist/{Id}")
	public String openUpdateReceptionist(@PathVariable("Id") Long Id, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		httpSession.setAttribute("RId", Id);
		Optional<User> findById = this.userRepository.findById(Id);
		User user = findById.get();
		model.addAttribute("user", user);
		return "admin/UpdateReceptionist";
	}

	@PostMapping("/updateReceptionist")
	public String UpdateReceptionist(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		try {
			Long RId = (Long) httpSession.getAttribute("RId");
			// System.out.println("DID " + DId);
			Role roleByName = this.roleRepository.getRoleByName("Receptionist");
			// System.out.println(roleByName);
			user.setCreated_DateTime(new Date());
			user.setModified_DateTime(new Date());
			user.setRole(roleByName);
			// System.out.println("USER " + user.getId());
			user.setId(RId);
			User user2 = this.userRepository.save(user);

			model.addAttribute("user", user2);
			model.addAttribute("name", user.getUserName());
			httpSession.setAttribute("message", new Message("Receptionist Updated Successfully.. ", "alert-success"));
			return "admin/UpdateReceptionist";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", user);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/UpdateReceptionist";
		}

	}

	@RequestMapping(value = "/deleteReceptionist/{page}", method = RequestMethod.POST)
	public String DeleteReceptionist(@PathVariable("page") Integer page,
			@RequestParam(value = "cb", required = false) Long[] cb, HttpSession httpSession, Model model) {

		if (cb == null) {
			httpSession.setAttribute("error",
					new Message("No Receptionist Data To be Deleted OR You Have not Seleted Box", "alert-danger"));

		}

		Pageable pageable = PageRequest.of(page, 2);
		Page<User> allDoctorsById = this.userRepository.getAllReceptionistById(pageable);

		model.addAttribute("receptionist", allDoctorsById);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allDoctorsById.getTotalPages());
		this.userRepository.BulkDeleteReceptionistById(cb);

		return "redirect:/admin/receptionist_list/{page}";
	}

	@RequestMapping(value = "/search_receptionist/{page}", method = RequestMethod.POST)
	public String SearchReceptionist(@PathVariable("page") Integer page, @Param("lastname") String lastname,
			@Param("email") String email, Model model, HttpSession httpSession) {
		try {
			System.out.println(lastname + " " + email);
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);

			Pageable pageable = PageRequest.of(page, 2);
			Page<User> allDoctorsById = this.userRepository.getAllReceptionistById(pageable);
			model.addAttribute("receptionist", allDoctorsById);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", allDoctorsById.getTotalPages());
			List<User> search = this.userRepository.SearchRecep(lastname, email);
			System.out.println(search.toString() + " " + search.toArray());
			model.addAttribute("receptionist", search);
			// model.addAttribute("doctor", searchByQualification);

			return "admin/ReceptionistList";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "admin/ReceptionistList";
	}
	// Patient Methods And Controllers Start

	@GetMapping("/add_patient")
	public String openPatientForm(Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		return "admin/AddPatient";
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
					return "admin/AddPatient";
				}
			}
			Patient patient2 = this.patientRepository.save(patient);
			model.addAttribute("patient", patient2);
			// model.addAttribute("name", patient.getFirstName());
			httpSession.setAttribute("message", new Message("Patient Added Successfully.. ", "alert-success"));
			return "admin/AddPatient";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("user", patient);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/AddPatient";
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
		return "admin/PatientList";
	}

	@GetMapping("/update_patient/{Id}")
	public String openUpdatePatient(@PathVariable("Id") Long Id, Model model, HttpSession httpSession) {
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		httpSession.setAttribute("PId", Id);
		Optional<Patient> findById = this.patientRepository.findById(Id);
		Patient patient = findById.get();
		model.addAttribute("patient", patient);
		return "admin/UpdatePatient";
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
			return "admin/UpdatePatient";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("patient", patient);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/UpdatePatient";
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

		return "redirect:/admin/patient_list/{page}";
	}

	@RequestMapping(value = "/search_patient/{page}", method = RequestMethod.POST)
	public String SearchPatient(@PathVariable("page") Integer page, @Param("lastname") String lastname,
			@Param("city") String city, Model model, HttpSession httpSession) {
		try {
			System.out.println(lastname + " " + city);
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);

			Pageable pageable = PageRequest.of(page, 2);
			Page<Patient> allPatient = this.patientRepository.getAllPatient(pageable);
			model.addAttribute("patient", allPatient);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", allPatient.getTotalPages());
			List<Patient> search = this.patientRepository.Search(lastname, city);
			System.out.println(search.toString() + " " + search.toArray());
			model.addAttribute("patient", search);
			// model.addAttribute("doctor", searchByQualification);

			return "admin/PatientList";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "admin/PatientList";
	}
	// Appointment Methods And Controllers Start

	@GetMapping("/add_appointment")
	public String openAppointmentForm(Model model, HttpSession httpSession) {
		List<User> allDoctors = this.userRepository.getAllDoctors();
		System.out.println("All Doctors " + allDoctors);
		model.addAttribute("Doctors", allDoctors);
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		return "admin/AddAppointment";
	}

	@PostMapping("/addAppointment")
	public String SaveAppointment(@ModelAttribute("appointment") Appointment appointment,
			@RequestParam("Email") String Email, Model model, HttpSession httpSession) {
		try {

			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			appointment.setCreated_DateTime(new Date());
			appointment.setModified_DateTime(new Date());
			// user.setRole(roleByName);
			/*
			 * List<Patient> emails = this.patientRepository.getEmails(); for (Patient
			 * elements : emails) { if
			 * (elements.getEmail_Id().equalsIgnoreCase(patient.getEmail_Id())) {
			 * httpSession.setAttribute("emailmessage", new
			 * Message("Email already registered . Try with Another", "alert-warning"));
			 * return "admin/AddPatient"; } }
			 */
			String subject = "APPOINTMENT-ALERT";
			String message = "Your Appointment has been conformed With Doctor : " + appointment.getDoctor_Name() + "!!";

			boolean sendEmail = this.emailService.sendEmail(subject, message, Email);
			System.out.println(sendEmail);
			Appointment appointment2 = this.appointmentRepository.save(appointment);
			model.addAttribute("appointment", appointment2);
			// model.addAttribute("name", patient.getFirstName());
			httpSession.setAttribute("message", new Message("Appointment Added Successfully.. ", "alert-success"));
			return "admin/AddAppointment";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("appointment", appointment);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/AddAppointment";
		}

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
		return "admin/AppointmentList";
	}

	@GetMapping("/update_appointment/{Id}")
	public String openUpdateAppointment(@PathVariable("Id") Long Id, Model model, HttpSession httpSession) {
		List<User> allDoctors = this.userRepository.getAllDoctors();
		System.out.println("All Doctors " + allDoctors);
		model.addAttribute("Doctors", allDoctors);
		String username = (String) httpSession.getAttribute("username");
		model.addAttribute("name", username);
		httpSession.setAttribute("AId", Id);
		Optional<Appointment> findById = this.appointmentRepository.findById(Id);
		Appointment appointment = findById.get();
		model.addAttribute("appointment", appointment);
		return "admin/UpdateAppointment";
	}

	@PostMapping("/updateAppointment")
	public String UpdateAppointment(@ModelAttribute("appointment") Appointment appointment, Model model,
			HttpSession httpSession) {
		try {
			Long AId = (Long) httpSession.getAttribute("AId");
			appointment.setCreated_DateTime(new Date());
			appointment.setModified_DateTime(new Date());
			appointment.setId(AId);
			Appointment save = this.appointmentRepository.save(appointment);
			model.addAttribute("appointment", save);
			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);
			httpSession.setAttribute("message", new Message("Appointment Updated Successfully.. ", "alert-success"));
			return "admin/UpdateAppointment";
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("appointment", appointment);
			httpSession.setAttribute("message", new Message("Something went wrong!! try again", "alert-danger"));
			return "admin/UpdateAppointment";
		}

	}

	@RequestMapping(value = "/deleteAppointment/{page}", method = RequestMethod.POST)
	public String DeleteAppointment(@PathVariable("page") Integer page,
			@RequestParam(value = "cb", required = false) Long[] cb, HttpSession httpSession, Model model) {

		if (cb == null) {
			httpSession.setAttribute("error",
					new Message("No Appointment To be Deleted OR You Have not Seleted Box", "alert-danger"));

		}

		Pageable pageable = PageRequest.of(page, 2);
		Page<Appointment> allAppointments = this.appointmentRepository.getAllAppointments(pageable);
		model.addAttribute("appointment", allAppointments);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allAppointments.getTotalPages());
		this.appointmentRepository.DeleteAllById(cb);

		return "redirect:/admin/appointment_list/{page}";
	}

	@RequestMapping(value = "/search_appointment/{page}", method = RequestMethod.POST)
	public String SearchAppointment(@PathVariable("page") Integer page, @Param("firstname") String firstname,
			@Param("lastname") String lastname, Model model, HttpSession httpSession) {
		try {

			String username = (String) httpSession.getAttribute("username");
			model.addAttribute("name", username);

			Pageable pageable = PageRequest.of(page, 2);
			Page<Appointment> allAppointments = this.appointmentRepository.getAllAppointments(pageable);
			model.addAttribute("appointment", allAppointments);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", allAppointments.getTotalPages());
			List<Appointment> search = this.appointmentRepository.Search(firstname, lastname);
			System.out.println(search.toString() + " " + search.toArray());
			model.addAttribute("appointment", search);
			// model.addAttribute("doctor", searchByQualification);

			return "admin/AppointmentList";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "admin/AppointmentList";
	}
}