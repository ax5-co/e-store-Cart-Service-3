package com.execise.estore.estore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2, message="Title should have at least 2 characters")
	private String title;
	
	@Size(min=2, message="Product Description should have at least 2 characters")
	private String description;
	
	private String color;
	
	private BigDecimal price;
	
	private boolean isNew=true;
	
	private boolean isFeatured=false;
	
	private boolean isActive=true;
	
	@Size(min=2, message="Meta title should have at least 2 characters")
	private String metaTitle;
	
	private String slug;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updatedAt;
	
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	private Category category;
	
//	private String categoryName;
	
	@ManyToMany(mappedBy = "products", fetch=FetchType.LAZY)
	private Set<Tag> tags = new HashSet<>();
	
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	private Brand brand;
	
//	private String brandName;
	
	@OneToMany
	@JsonBackReference
//	@OneToMany(mappedBy = "product")
	private Set<ProductVariant> productVariant
			= new HashSet<>();

//	private Set<String> productVariantsSizes= new HashSet<>();
	
	@JsonIgnore
	@Lob
	private byte[] image;
	

}