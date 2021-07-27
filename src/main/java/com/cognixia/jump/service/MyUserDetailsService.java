package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;

//https://stackoverflow.com/questions/49617349/there-is-no-passwordencoder-mapped-for-the-id-null-with-database-authenticatio

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepo userRepo;
	
	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userFound = userRepo.findByUsername(username);
		if(!userFound.isPresent()) { //if NOT found
			throw new UsernameNotFoundException("No user with username: " + username);
		}
		return new MyUserDetails(userFound.get()); //if found, return user found
	}*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    BCryptPasswordEncoder encoder = passwordEncoder();
	    Optional<User> user = userRepo.findByUsername(username);
	    if(!user.isPresent()) { //if NOT found
			throw new UsernameNotFoundException("No user with username: " + username);
		}
	    return new org.springframework.security.core.userdetails.User(user.get().getUsername(),encoder.encode(user.get().getPassword()), new MyUserDetails(user.get()).getAuthorities());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
