package example.userauth.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{

	String entityName;
	String fieldName;
	String fieldValue;
	
	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String entityName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s",entityName,fieldName,fieldValue));
		this.entityName = entityName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
}
