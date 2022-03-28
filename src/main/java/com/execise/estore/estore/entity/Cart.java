package com.execise.estore.estore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

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
@Getter
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //sets this as a primary key
	private Long id;
	
	@Column(unique = true, updatable = false, columnDefinition = "BINARY(16)")
	private UUID uuid;
	
	
	@OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)	
	@JsonManagedReference
	private Set<CartItem> cartItems = new HashSet<>();
	
	private BigDecimal total = BigDecimal.ZERO;
	
	@CreationTimestamp
	private Date createdAt;
	
	@Enumerated(EnumType.STRING)
	private CartStatus cartStatus = CartStatus.OPEN;
	
	@ManyToOne(fetch = FetchType.LAZY) //uni-directional
	@JoinColumn(name = "fk_userName", referencedColumnName = "userName") 
	@JsonBackReference
	private User user;		//I want to refer to the owner user with userName.   //usually one to one
	
	private boolean isActive = true;

	@OneToOne(mappedBy = "sourceCart", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	@JsonBackReference
    private CustomerOrder customerOrder;
	
	//helper method
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
		cartItem.setCart(this);
	}


	@Override
	public String toString() {
		return "Cart [id=" + id + ", uuid=" + uuid + ", cartItems=" + cartItems + ", total=" + total + ", createdAt="
				+ createdAt + ", cartStatus=" + cartStatus + ", user=" + user + ", isActive=" + isActive + "]";
	}
	
	
}
