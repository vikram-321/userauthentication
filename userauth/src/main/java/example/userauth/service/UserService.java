package example.userauth.service;

import java.util.List;

import example.userauth.entity.User;
import example.userauth.payload.UserDto;

public interface UserService{

	public UserDto addUser(UserDto userDto);
	
	public List<UserDto> getAllUsers();
	
	public UserDto getUser(String userId);
	
	public UserDto updateUser(UserDto user);
	
	public void deleteUser(String userId);
	
}
