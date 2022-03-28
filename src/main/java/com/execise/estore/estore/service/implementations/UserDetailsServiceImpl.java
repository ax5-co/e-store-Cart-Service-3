package com.execise.estore.estore.service.implementations;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		
		User user = userOptional.orElseThrow(() -> 
					new UsernameNotFoundException("No user Found with username : "
							+ username));
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPasswordHash(),
				user.isEnabled(), true, true, true, getAuthorities("USER")
				);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return singletonList(new SimpleGrantedAuthority(role));
	}
	

}
