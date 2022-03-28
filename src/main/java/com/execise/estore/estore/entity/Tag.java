package com.execise.estore.estore.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;

	private String metaTitle;

	private String slug;

	private String description;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY ,cascade=CascadeType.PERSIST)
	@JoinTable(name="tags_products",
		joinColumns = {
				@JoinColumn(name = "product_id", referencedColumnName = "id",
						nullable = false, updatable = false)},
		inverseJoinColumns = {
				@JoinColumn(name = "tag_id", referencedColumnName = "id",
						nullable = false, updatable = false)}
			)
	private Set<Product> products = new HashSet<>();
	
	public Tag() {
		super();
	}

	public Tag(Long id, String title, String metaTitle, String slug, String description, Set<Product> products) {
		super();
		this.title = title;
		this.metaTitle = metaTitle;
		this.slug = slug;
		this.description = description;
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
}
