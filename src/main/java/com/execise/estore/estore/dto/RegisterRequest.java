package com.execise.estore.estore.dto;
//in package dto: data transfer object

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String email;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
}
