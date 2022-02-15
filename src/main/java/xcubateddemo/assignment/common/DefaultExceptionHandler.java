package xcubateddemo.assignment.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@Value("${app.general.error :Something Went Wrong}")
	String defaultMessage;

	/* <======Project Specific Exceptions======> */
	@ExceptionHandler(value = { NumericInputException.class })
	public ResponseEntity<Object> handleNumericInputException(NumericInputException ex, WebRequest request) {

		log.info("NumericInputException : " + ex.toString());
		ex.printStackTrace();

		ExceptionDetails exceptionEntity = new ExceptionDetails();
		exceptionEntity.setMsg(ex.getMessage() != null ? ex.getMessage() : defaultMessage);
		exceptionEntity.setStatus(Status.FAILED);
		return handleExceptionInternal(ex, exceptionEntity, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/* <======Basic Exceptions======> */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> defaultException(Exception ex, WebRequest request) {

		log.info("Exception : " + ex.toString());
		ex.printStackTrace();

		ExceptionDetails exceptionEntity = new ExceptionDetails();
		exceptionEntity.setMsg(ex.getMessage() != null ? ex.getMessage() : defaultMessage);
		exceptionEntity.setStatus(Status.FAILED);
		return handleExceptionInternal(ex, exceptionEntity, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.info("MethodArgumentNotValidException : " + ex.toString());
		ex.printStackTrace();

		String message = "";
		FieldError error = ex.getBindingResult().getFieldError();
		if (error != null) {
			message = error.getDefaultMessage();
		} else {
			ObjectError objError = ex.getBindingResult().getGlobalError();
			if (objError != null) {
				message = objError.getDefaultMessage();
			}
		}
		ExceptionDetails exceptionEntity = new ExceptionDetails();
		exceptionEntity.setMsg(message);
		exceptionEntity.setStatus(Status.FAILED);
		return handleExceptionInternal(ex, exceptionEntity, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (!(body instanceof ExceptionDetails)) {
			log.info("System Exception : " + ex.toString());
			ex.printStackTrace();
			body = new ExceptionDetails(ex.getMessage());
		}

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
		}

		headers.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<>(body, headers, status);
	}

}
