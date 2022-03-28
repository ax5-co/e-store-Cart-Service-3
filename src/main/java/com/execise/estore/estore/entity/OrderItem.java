package com.execise.estore.estore.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;

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
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_cart")
	private Cart cart;
	
	//Use a mapper to fill the fields from the cart item
	//WITHOUT having a relation to the cartItem to avoid 
	//the need to keep cart Items in db.

	private String productName;
	
	private String productColor;
	
	private String productVariantSize; 
	
	private BigDecimal productUnitPrice; 
	
	//I will make this a uni-directional association
	//since a product variant does not need to know which order items reference it
	//we keep the variant to have a link to its stock.
	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "fk_productVariant")
	private ProductVariant productVariant;
	
	//duplication of data, but this so that I don't need to query the product itself
	
	@Min(value = 1, message = "Cannot have 0 quantity of an order item")
	private int quantity;
	
	private BigDecimal itemTotal;
	
	@CreationTimestamp
	private Date createdAt;
	
	private boolean inStock = true;
	
	@ManyToOne(fetch = FetchType.LAZY)   //uni-directional
	@JoinColumn(name = "fk_userName", referencedColumnName = "userName")
	private User user;	 	
	
	@ManyToOne(fetch = FetchType.LAZY) //bi-directional
//	@JoinColumn(name = "fk_order", referencedColumnName = "number")
	@JoinColumn(name = "fk_order")
//	@JsonManagedReference
	private CustomerOrder customerOrder;


	public boolean checkInStock() {
		if(this.productVariant.getStockQuantity() > this.quantity)
			return true;
		return false;
	}
	
	public void updateItemTotal() {
		BigDecimal total = 
				this.productUnitPrice.multiply(
						BigDecimal.valueOf(this.quantity));
		
		if(checkInStock()) {
			itemTotal=total;
			this.inStock = true;
		}
		else
		{
			this.itemTotal = BigDecimal.ZERO;
			this.inStock = false;		
		}
		
	}
}
