package com.springboot.HM.MyConfiguration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Authentication WOrks");
		boolean hasDoctorRole = false;
		boolean hasAdminRole = false;
		boolean hasReceptionistRole = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		System.out.println(authorities.toString());
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("Admin")) {
				hasAdminRole = true;
				System.out.println(grantedAuthority.getAuthority().equals("Admin"));
				break;
			} else if (grantedAuthority.getAuthority().equals("Doctor")) {
				hasDoctorRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("Receptionist")) {
				hasReceptionistRole = true;
				break;
			}
		}
		if (hasAdminRole) {
			redirectStrategy.sendRedirect(request, response, "/Welcome");
		} else if (hasDoctorRole) {
			redirectStrategy.sendRedirect(request, response, "/Welcome");
		} else if (hasReceptionistRole) {
			redirectStrategy.sendRedirect(request, response, "/Welcome");
		} else {
			System.out.println("Error in Success Handler");
			throw new IllegalStateException();
		}
	}
}
