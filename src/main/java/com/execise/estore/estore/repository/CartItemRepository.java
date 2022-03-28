package com.execise.estore.estore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findAllByCart(Cart cart);

	Page<CartItem> findAllByCart(Cart cart, Pageable pageable);
	
	Page<CartItem> findAllByUser(User user, Pageable pageable);

	Page<CartItem> findAllByUserToken(String userToken, Pageable pageable);

	Page<CartItem> findAllByProduct(Product product, Pageable pageable);

	Page<CartItem> findAllByProductVariant(ProductVariant productVariant, Pageable pageable);

	Optional<CartItem> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);

	Optional<CartItem> findByUuid(UUID uuid);
	
	void deleteAllByCart(Cart cart);
	
	void deleteAllByUser(User user);
	
	void deleteAllByUuid(UUID uuid);

	List<CartItem> findAllByCartAndInStock(Cart cart, boolean b);
	
}
