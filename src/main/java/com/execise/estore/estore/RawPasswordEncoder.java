package com.execise.estore.estore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RawPasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "1111";
		System.out.println(encoder.encode(rawPassword));

	}

}
