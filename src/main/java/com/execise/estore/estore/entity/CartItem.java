package com.execise.estore.estore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, updatable = false, columnDefinition = "BINARY(16)")
	private UUID uuid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_cart")
	@JsonBackReference
	private Cart cart;
	
	//I will make this a uni-directional association
	//since a product does not need to know which cart items reference it
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@JsonBackReference
	private Product product;
	
	//I will make this a uni-directional association
	//since a product variant does not need to know which cart items reference it
	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "fk_productVariant")
	@JsonBackReference
	private ProductVariant productVariant;
	
	private BigDecimal unitPrice; //duplication of data, but this so that I don't need to query the product itself
	
	@Min(value = 1, message = "Cannot have 0 quantity of an item in cart")
	private int quantity;
	
	@CreationTimestamp
	private Date createdAt;
	
	private boolean inStock = true;
	
	@ManyToOne(fetch = FetchType.LAZY)   //uni-directional
	@JoinColumn(name = "fk_userName", referencedColumnName = "userName")
	@JsonBackReference
	private User user;	 	//duplication of data, but this so that I don't need to query the cart itself

	private String size;  //duplication of data, but this so that I don't need to query the product_variant itself

	
	public boolean checkInStock() {
		if(this.productVariant.getStockQuantity() >= this.quantity)
			return true;
		return false;
	}


	@Override
	public String toString() {
		return "CartItem [id=" + id + ", uuid=" + uuid + ", product=" + product + ", productVariant=" + productVariant
				+ ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", createdAt=" + createdAt + ", inStock="
				+ inStock + ", size=" + size + "]";
	}
	
	

}
