 package example.userauth.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.userauth.entity.User;
import example.userauth.exception.DataNotFoundInRequestBody;
import example.userauth.exception.UserAlreadyExists;
import example.userauth.exception.UserNotFoundException;
import example.userauth.payload.UserDto;
import example.userauth.repository.UserRepository;
import example.userauth.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDto addUser(UserDto userDto) {
		List<UserDto> userDtos = this.getAllUsers();
		if(userDto == null) {
			throw new DataNotFoundInRequestBody("Data Not Available in request body.");
		}
		else {
			User savedUser = null;
			for(UserDto tempUserDto:userDtos) {
				
				if(userDto.getUserId().equals(tempUserDto.getUserId())) {
					throw new UserAlreadyExists("User","Id",userDto.getUserId());
				}
			}	
					User tempuser = this.dtoToUser(userDto);
					
					savedUser=userRepository.save(tempuser);
			return this.userToDto(savedUser);
		}
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepository.findAll();
		List<UserDto> userDtos = userList.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto getUser(String userId) {
//		Optional<User> foundUser = userRepository.findById(userId);
//		User tempUser = null;
//		if(foundUser != null)
//			tempUser = foundUser.get();
//			if(tempUser.getUserId()==userId)
//				return tempUser;
		
		User user =  this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User","Id",userId));
		
		return this.userToDto(user);	
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		
//	    List<User> userList = new ArrayList<>();
//	    userList = getAllUsers();
//		for(User tempUser : userList) {
//			if(tempUser.getUserId() == user.getUserId()){
				User user= this.userRepository.findById(userDto.getUserId()).orElseThrow(()->new UserNotFoundException("User","Id",userDto.getUserId())); 
		
				user.setUserId(userDto.getUserId());
				user.setUserPassword(userDto.getUserPassword());
				
				User tempUser = this.userRepository.save(user);
				
				return this.userToDto(tempUser);
			}
//		}
//		User tempUser = userRepository.save(user);
//		return tempUser;
//	}

	@Override
	public void deleteUser(String userId) {
		User tempUser=this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User","Id",userId));
		this.userRepository.delete(tempUser);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setUserPassword(userDto.getUserPassword());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserPassword(user.getUserPassword());
		return userDto;
	}


}
