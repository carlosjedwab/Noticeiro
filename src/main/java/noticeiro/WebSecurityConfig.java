package noticeiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import noticeiro.service.AuthenticationService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/home", "/signup", "/forms").permitAll()
				.antMatchers("/api/**").hasRole("ADMIN")
				.anyRequest().authenticated()	
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/feed", true)
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
}
