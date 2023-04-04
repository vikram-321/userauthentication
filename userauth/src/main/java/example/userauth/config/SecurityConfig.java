package example.userauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import example.userauth.repository.UserRepository;
import example.userauth.security.CustomUserDetailService;
import example.userauth.security.JwtAuthenticationEntryPoint;
import example.userauth.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity 
public class SecurityConfig{
	
//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
	 	.csrf()
		.disable()
		.authorizeHttpRequests()
//		.requestMatchers("/**")
//		.permitAll()
		.requestMatchers(HttpMethod.POST,"/login").permitAll()
		.requestMatchers(HttpMethod.POST,"/register").permitAll()
		.requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USER","ADMIN")
		.requestMatchers(HttpMethod.GET,"/users/{userId}").hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.PUT,"/users").hasAnyRole("USER","ADMIN")
		.requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and().exceptionHandling()
		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
		return defaultSecurityFilterChain;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
