package example.userauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import example.userauth.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse> userNotFoundExceptionHandler(UserNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExists.class)
	public ResponseEntity<ApiResponse> userAlreadyExists(UserAlreadyExists ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(DataNotFoundInRequestBody.class)
	public ResponseEntity<ApiResponse> dataNotFoundInRequestBody(DataNotFoundInRequestBody ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException .class)
	public ResponseEntity<ApiResponse> httpMessageNotReadableEntity(HttpMessageNotReadableException ex){
		String msg="Required request body is missing";
		ApiResponse apiResponse = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUserOrPassword.class)
	public ResponseEntity<ApiResponse> invalidUserOrPassword(InvalidUserOrPassword ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
}
