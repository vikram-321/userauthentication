package example.userauth.exception;

@SuppressWarnings("serial")
public class InvalidUserOrPassword extends Exception{

	public InvalidUserOrPassword(String msg) {
		super(msg);
	}
}
