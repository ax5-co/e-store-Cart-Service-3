package com.execise.estore.estore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findCartByUser(User user);

	Optional<Cart> findByUuidAndUser(UUID cartUuid, User user);

	Optional<Cart> findCartByUserAndIsActive(User user, boolean b);

	List<Cart> findTop10ByUser(User user);
}
