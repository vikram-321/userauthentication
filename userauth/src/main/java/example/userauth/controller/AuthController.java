package example.userauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.userauth.entity.Roles;
import example.userauth.entity.User;
import example.userauth.exception.DataNotFoundInRequestBody;
import example.userauth.exception.InvalidUserOrPassword;
import example.userauth.exception.UserAlreadyExists;
import example.userauth.payload.JwtAuthRequest;
import example.userauth.payload.JwtAuthResponse;
import example.userauth.payload.UserDto;
import example.userauth.repository.UserRepository;
import example.userauth.security.CustomUserDetailService;
import example.userauth.security.JwtTokenHelper;
import example.userauth.serviceimpl.AuthControllerService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthControllerService authControllerService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		return this.authControllerService.authTokenGenerator(request);
	}
	
	@PostMapping("/register")
	public ResponseEntity<JwtAuthResponse> registerUser(@RequestBody User user){			
		return this.authControllerService.registerUser(user);
		}
	
	@PutMapping("/users")
	public ResponseEntity<JwtAuthResponse> updateUser(@RequestBody User user){
		return this.authControllerService.updateUser(user);
	}
}
