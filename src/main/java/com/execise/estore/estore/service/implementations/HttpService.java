package com.execise.estore.estore.service.implementations;

import static com.execise.estore.estore.Constants.MY_FATOORAH_AUTHORIZATION_HEADER_VALUE;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpService<T> {

	public HttpEntity<?> initiateRequest(T request) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				MY_FATOORAH_AUTHORIZATION_HEADER_VALUE);
		headers.setAccept(Collections
				.singletonList(MediaType.APPLICATION_JSON));

		return new HttpEntity<T>(
						request, headers);
	}

}
