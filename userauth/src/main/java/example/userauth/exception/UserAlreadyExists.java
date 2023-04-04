package example.userauth.exception;

@SuppressWarnings("serial")
public class UserAlreadyExists extends RuntimeException{
	String entityName;
	String fieldName;
	String fieldValue;
	
	public UserAlreadyExists() {
		super();
	}

	public UserAlreadyExists(String entityName, String fieldName, String fieldValue) {
		super(String.format("%s is already available with %s : %s",entityName,fieldName,fieldValue));
		this.entityName = entityName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
}
