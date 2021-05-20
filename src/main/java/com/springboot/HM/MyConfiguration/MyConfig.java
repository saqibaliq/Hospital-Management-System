package com.springboot.HM.MyConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class MyConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RoleAuthenticationSuccessHandler roleAuthentication;

	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}

	/*
	 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */

	@SuppressWarnings("deprecation")
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.getUserDetailService());
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return authenticationProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.config.
	 * annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.config.
	 * annotation.web.builders.WebSecurity)
	 */
	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**",
	 * "/js/**", "/images/**");
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.config.
	 * annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.httpBasic().and().logout().logoutUrl("/logout").logoutSuccessUrl("/").and().csrf().disable();
	}
}
