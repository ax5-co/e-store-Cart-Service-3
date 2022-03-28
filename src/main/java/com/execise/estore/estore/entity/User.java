package com.execise.estore.estore.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
//@Getter
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20)
	private String firstName;
	
	@Column(length = 20)
	private String lastName;

	@Column(length = 20, unique = true, updatable = false)
	@NotBlank(message = "Username is required, maximum 20 characters")
	//TODO this should be just: username (not Name), but i'm not changing that now.
	private String userName;
	
	@NotBlank(message = "Password is required")
	//but a password should be about 10 characters (originally entered by user)
	//TODO this should be just: password, but i'm not changing that now.
	private String passwordHash;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "E-mail must be of right e-mail format and be unique")
	private String email;
	
	private int phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "user")
    @JsonManagedReference			//Never use @JsonBckReference on a collection.
	private Set<Address> addresses=new HashSet<>();
	
	//I don't know yet how the jwt will be used
	private String token;		//I wanted to refer to carts and orders with this token, but no longer
	
	private boolean enabled;
	
	private Instant createdAt;

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public Role getRole() {
		return role;
	}

    @JsonManagedReference			//Never use @JsonBckReference on a collection.
	public Set<Address> getAddresses() {
		return addresses;
	}

	public String getToken() {
		return token;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
	
	
}
