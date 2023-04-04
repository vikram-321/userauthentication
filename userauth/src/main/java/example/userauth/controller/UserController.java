package example.userauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.userauth.payload.ApiResponse;
import example.userauth.payload.UserDto;
import example.userauth.service.UserService;

@RestController
@RequestMapping()
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
		return ResponseEntity.ok(this.userService.getUser(userId));
	}
	
//	@PostMapping("/users")
//	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
//		UserDto savedUser = this.userService.addUser(userDto);
//		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
//	}
	
//	@PutMapping("/users")
//	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
//		UserDto updatedUser = this.userService.updateUser(userDto);
//		return ResponseEntity.ok(updatedUser);
//	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
			this.userService.deleteUser(userId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Use Deleted Successfully", true),HttpStatus.OK);
	}
}
