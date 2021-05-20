package com.springboot.HM.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.HM.dao.UserRepository;
import com.springboot.HM.entities.User;
import com.springboot.HM.helper.Message;

@Controller
public class WelcomeController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String welcome(Model model) {
		model.addAttribute("title", "Welcome - HMS");
		return "Welcome";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login");

		return "Login";
	}

	@PostMapping("/doLogin")
	public String Signin(@RequestParam("username") String user_name, @RequestParam("password") String password,
			Model model, HttpSession httpSession) {

		try {
			User user = this.userRepository.Login(user_name, password);
			if (user == null) {
				httpSession.setAttribute("message",
						new Message("Something went wrong!! Please Refresh and Try Again", "alert-danger"));
				httpSession.setAttribute("user", user);
			} else {
				if (user.getRole().getId() == 1 || user.getRole().getRoleName().equals("Admin")) {
					System.out.println("Inside Admin Panel");
					model.addAttribute("name", user.getUserName());
					model.addAttribute("user", user);
					// user.getRole().setRoleDesc("ROLE_ADMIN");
					System.out.println(user.getId());
					httpSession.setAttribute("username", user.getUserName());

					return "admin/Welcome";
				} else if (user.getRole().getId() == 2) {
					model.addAttribute("name", user.getUserName());
					model.addAttribute("user", user);
					httpSession.setAttribute("username", user.getUserName());
					return "doctor/Welcome";
				} else if (user.getRole().getId() == 3) {
					model.addAttribute("name", user.getUserName());
					model.addAttribute("user", user);
					httpSession.setAttribute("username", user.getUserName());
					return "receptionist/Welcome";
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			httpSession.setAttribute("message",
					new Message("Something went wrong!! Please Refresh and Try Again", "alert-danger"));
		}
		return "Login";
	}

	@RequestMapping(value = { "/logout" })
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "Welcome";
	}
}
