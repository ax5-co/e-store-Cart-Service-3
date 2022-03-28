package com.execise.estore.estore.entity;

import java.sql.Blob;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;

	private String metaTitle;

	private String description;
	
	@JsonIgnore
	@Lob
	private byte[] bannerImage;
	
	@OneToMany
	private Set<Product> products = new HashSet<>();
	
	public Brand(){
		super();
	}

	public Brand(String title, String metaTitle, String description, byte[] bannerImage, Set<Product> products) {
		super();
		this.title = title;
		this.metaTitle = metaTitle;
		this.description = description;
		this.bannerImage = bannerImage;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(byte[] bannerImage) {
		this.bannerImage = bannerImage;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", title=" + title + ", metaTitle=" + metaTitle + ", description=" + description
				+ ", bannerImage=" + Arrays.toString(bannerImage) + "]";
	}

	
	
}
