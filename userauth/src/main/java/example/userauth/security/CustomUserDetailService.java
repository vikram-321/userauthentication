package example.userauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import example.userauth.entity.User;
import example.userauth.exception.UserNotFoundException;
import example.userauth.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UserNotFoundException {
		
	User user =	this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User","ID",userId));
		return user;
	}
	
}
