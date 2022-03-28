package com.execise.estore.estore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.Role;
import com.execise.estore.estore.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional <User> findById(Long userId);
	
	Optional <User> findByToken(String token);
	
//	@Query ("SELECT u FROM User u WHERE u.userName = ?1")
//	User findByUsername(String userName);
	
	Optional<User> findByUserName(String userName);
	
	@Query ("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);
	
	Page <User> findAllByRole(Role role, Pageable pageable);

	
}
