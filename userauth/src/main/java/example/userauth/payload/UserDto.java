package example.userauth.payload;

public class UserDto {

	private String userId;
	private String userPassword;
	
	public UserDto() {
		super();
	}
	
	public UserDto(String userId, String userPassword) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
	}

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString(){
		return "UserDto [userId=" + userId + ", UserPassword=" + userPassword + "]";
	}
	
}
