package com.execise.estore.estore.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configurable
public class ValidationConfigurer {
/*
 * from: https://stackoverflow.com/questions/49334872/convert-setconstraintviolationt-to-springs-bindingresults-or-errors
 * to help handle ConstraintViolationException in RestExceptionHandler
 * the way we handle MethodArgumentNotValidException in same handler
 */
	
	@Bean
	  public javax.validation.Validator localValidatorFactoryBean() {
	    return new LocalValidatorFactoryBean();
	  }
}
