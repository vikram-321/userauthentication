package example.userauth.payload;

public class JwtAuthRequest {

	private String userId;
	
	private String userPassword;

	public JwtAuthRequest() {
		super();
	}

	public JwtAuthRequest(String userId, String userPassword) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUsername(String username) {
		userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "JwtAuthRequest [Username=" + userId + ", password=" + userPassword + "]";
	}
	
	
	
}
