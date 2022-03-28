package com.execise.estore.estore.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
//@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
	private User user;
	@NotNull
	@Size(min = 1, max = 50)	
	private String title;
	@NotNull
	@Size(min = 1, max = 15)		
	private String governorate;
	@NotNull
	@Size(min = 1, max = 15)	
	private String area;
	
	private int block;
	
	private String street;
	
	private String building;
	
	private int floor;
	
	private int apartment;
	
	private String notes;

	public Long getId() {
		return id;
	}

    @JsonBackReference
	public User getUser() {
		return user;
	}

	public String getTitle() {
		return title;
	}

	public String getGovernorate() {
		return governorate;
	}

	public String getArea() {
		return area;
	}

	public int getBlock() {
		return block;
	}

	public String getStreet() {
		return street;
	}

	public String getBuilding() {
		return building;
	}

	public int getFloor() {
		return floor;
	}

	public int getApartment() {
		return apartment;
	}

	public String getNotes() {
		return notes;
	}
	
	
}
