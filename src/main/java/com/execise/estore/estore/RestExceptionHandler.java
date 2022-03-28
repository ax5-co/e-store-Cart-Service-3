package com.execise.estore.estore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.execise.estore.estore.utils.RestException;
import com.execise.estore.estore.utils.RestMessage;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

//to make this handler global, we use: @ControllerAdvice
//It enables developers to apply, among other things, @ExceptionHandler methods
//globally to all controllers in an application.
//so all methods defined here will apply to all @Controller in the application
//so we avoid the need to define a base class for all controllers to extend or so
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
	private static final String JWTEXPIRY_ERROR = "Exception.unauthorized";
	private static final String UNEXPECTED_ERROR = "Exception.unexpected";
	private final MessageSource messageSource;

	@Autowired
	private Validator validator;

	@Autowired
	public RestExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(RestException.class)
	public ResponseEntity<RestMessage> handleIllegalArgument(RestException ex, Locale locale) {
		String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
		return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestMessage> handleArgumentNotValidException(MethodArgumentNotValidException ex,
			Locale locale) {
		BindingResult result = ex.getBindingResult();
		List<String> errorMessages = result.getAllErrors()
				.stream()
				.map(objectError -> messageSource
						.getMessage(objectError, locale))
				.collect(Collectors.toList());
		
		log.info("error messages include: ");
		for (String errorMessage : errorMessages) {
			log.info(errorMessage);
		}
		
		return new ResponseEntity<>(new RestMessage(errorMessages),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<RestMessage> handleValidationFailedException(ConstraintViolationException ex, Locale locale) {
		List<String> errors = new ArrayList<String>();
		

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			log.info("errors adding: "+violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()+"."+violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath());
			errors.add(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()+"."+violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath());
		
		}		
		
//		BindingResult bindingResult = new BeanPropertyBindingResult(null, null);
//
//		validator.validate(ex, bindingResult);
//
//		log.info("bindingResult All errors include: ");
//		for (ObjectError error : bindingResult.getAllErrors()) {
//			log.info(error.getCode());
//		}
		
		//problems with the following part:
		// - The parameters is not working
		// - The errors.get(0) means we have a single violation error for sure.
		
		List<String> errorMessages = ex.getConstraintViolations().stream()
				.map(violation -> messageSource
						.getMessage(errors.get(0), violation.getExecutableParameters(), locale))
				.collect(Collectors.toList());
		
		log.info("error messages include: ");
		for (String errorMessage : errorMessages) {
			log.info(errorMessage);
		}
		
		return new ResponseEntity<>(new RestMessage(errorMessages),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<RestMessage> handleExpiredJwtExceptions(Exception ex, Locale locale) {
		String errorMessage = messageSource.getMessage(JWTEXPIRY_ERROR, null, locale);
		return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.UNAUTHORIZED);
	}
	
	// the following method handles the Exception exceptions
	// which is very general, why?
	// because it is responsible to handle the exceptions we did not expect at all
	// i.e., every exception other than we handled above.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
		String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
		ex.printStackTrace(); // 'cuz it was unexpected, we log all details
		return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}