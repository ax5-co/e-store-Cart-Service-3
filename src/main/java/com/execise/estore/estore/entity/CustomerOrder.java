package com.execise.estore.estore.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerOrder {
	
	@Id
	@Column(name = "cart_id")
	private Long id;
	
	
	//for now, let's use a random UUID as the OrderNumber
	@Column(unique = true, updatable = false, columnDefinition = "BINARY(16)")
	private UUID number = UUID.randomUUID();
	
	@CreationTimestamp
	private Date createdAt;
	private BigDecimal cartTotal;
	private BigDecimal shippingTotal;
	private BigDecimal orderTotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_shipping_address")
	private Address shippingAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payment_method")
	private PaymentMethod paymentMethod;
	
	@OneToMany(mappedBy = "customerOrder", orphanRemoval = true, cascade = CascadeType.ALL)
//	@JsonBackReference
	private Set<OrderItem> orderItems;
	
	@ManyToOne(fetch = FetchType.LAZY)   //uni-directional
	@JoinColumn(name = "fk_userName", referencedColumnName = "userName")
	private User user;
	
	//every order must have a cart, but not every cart will have an order connected to it
	//so let's consider the order (that has the foriegn key to cart) as the CHILD
	//and cart is the PARENT
	//But, do we need it bidirectional? NO. and it is better to have the order's 
	//PK (@Id field) = cart's foriegn key! (better performance), to do so, follow this link:
	//https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
	@OneToOne
	@MapsId
	@JoinColumn(name = "cart_id")
	@JsonManagedReference
	private Cart sourceCart;

}
