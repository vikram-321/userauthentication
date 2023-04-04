package example.userauth.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import example.userauth.entity.Roles;
import example.userauth.entity.User;
import example.userauth.exception.DataNotFoundInRequestBody;
import example.userauth.exception.InvalidUserOrPassword;
import example.userauth.exception.UserAlreadyExists;
import example.userauth.exception.UserNotFoundException;
import example.userauth.payload.JwtAuthRequest;
import example.userauth.payload.JwtAuthResponse;
import example.userauth.repository.UserRepository;
import example.userauth.security.CustomUserDetailService;
import example.userauth.security.JwtTokenHelper;
import lombok.var;

@Service
public class AuthControllerService {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<JwtAuthResponse> authTokenGenerator(JwtAuthRequest request){
//			this.authenticate(request.getUserId(),request.getUserPassword());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUserId(), request.getUserPassword());
			this.authenticationManager.authenticate(authenticationToken);
			UserDetails userDetails  = this.customUserDetailService.loadUserByUsername(request.getUserId()); 
			JwtAuthResponse response = new JwtAuthResponse();
//			System.out.println(userDetails.getUsername() + "  "+userDetails.getPassword());
//		if(userDetails.getUsername().equals(request.getUserId()) && (passwordEncoder.encode(request.getUserPassword())).equals(userDetails.getPassword()))
//		{
			String token = this.jwtTokenHelper.generateToken(userDetails);
			response.setToken(token);
			return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
//		}
//		else
//		{
//			return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.BAD_REQUEST);
//		}
	}
	
//	private void authenticate(String username,String password) throws Exception{
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//		    try {
//		    this.authenticationManager.authenticate(authenticationToken);
//		    }
//		    catch(BadCredentialsException ex) {
//		    System.out.println("Invalid Details...!");
//			throw new BadCredentialsException("Invalid Username or Password...!");
//		    }
//		}
	
	public ResponseEntity<JwtAuthResponse> registerUser(User user){
		List<User> userList = this.userRepository.findAll();
		if(user == null) {
			throw new DataNotFoundInRequestBody("Data Not Available in request body.");
		}
		else {
			for(User tempUser:userList) {
				
				if(user.getUserId().equals(tempUser.getUserId())) {
					throw new UserAlreadyExists("User","Id",user.getUserId());
				}
			}	
			User registerUser  = new User();
			registerUser.setUserId(user.getUserId());
			registerUser.setUserPassword(passwordEncoder.encode(user.getPassword()));
			registerUser.setRole(Roles.ROLE_USER);
			userRepository.save(registerUser);
			String token = this.jwtTokenHelper.generateToken(registerUser);
			JwtAuthResponse response= new JwtAuthResponse();
			response.setToken(token);
			return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
	}

	public ResponseEntity<JwtAuthResponse> updateUser(User user) {
		User tempUser = this.userRepository.findById(user.getUserId()).orElseThrow(()->new UserNotFoundException("User","Id",user.getUserId()));
		tempUser.setUserId(user.getUserId());
		tempUser.setUserPassword(user.getPassword());
		tempUser.setRole(Roles.ROLE_USER);
		userRepository.save(tempUser);
		String token = this.jwtTokenHelper.generateToken(tempUser);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
}
