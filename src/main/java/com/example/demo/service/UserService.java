package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.util.CustomException;

import lombok.NonNull;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String signup(User user) throws CustomException {
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		if (!repository.existsByUsername(username) && !repository.existsByEmail(email)) {
			user.setId(Uuids.timeBased());
			Date creationDate = new Date();
			user.setCreatedOn(creationDate);
			user.setModified(creationDate);
			user.setPassword(passwordEncoder.encode(password));
			List<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_CLIENT);
			user.setRoles(roles);
			repository.save(user);
			return jwtTokenProvider.createToken(username, repository.findByUsername(username).getRoles());
		} else if (repository.existsByUsername(username)) {
			throw new CustomException("Username already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (repository.existsByEmail(email)){
			throw new CustomException("Email already registered", HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			throw new CustomException("Something went wrong! Please try again later.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public String signin(JwtRequest jwtRequest) throws CustomException {
		try {
			String username = jwtRequest.getUsername();
			String password = jwtRequest.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = repository.findByUsername(username);
			user.setLastLogin(new Date());
			repository.save(user);
			return jwtTokenProvider.createToken(username, repository.findByUsername(username).getRoles());
		} catch (AuthenticationException ex) {
			throw new CustomException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception ex) {
			throw new CustomException("Something went wrong! Please try again later.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public String updateUser(User user, String username) throws CustomException {
		if (repository.existsByUsername(username)) {
			User existingUser = repository.findByUsername(username);
			user.setId(existingUser.getId());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreatedOn(existingUser.getCreatedOn());
			user.setModified(new Date());
			List<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_CLIENT);
			user.setRoles(roles);
			repository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), roles);
		} else {
			throw new CustomException("User does not Exist", HttpStatus.NOT_FOUND);
		}
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public User findByUsername(String username) throws CustomException {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new CustomException("The user does not exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public void deleteUserById(@NonNull UUID uuid) {
		if (repository.existsById(uuid)) {
			repository.deleteById(uuid);
		} else if (!repository.existsById(uuid)) {
			throw new CustomException("User does not Exist", HttpStatus.NOT_FOUND);
		} else {
			throw new CustomException("Something went wrong! Please try again later.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public void deleteUserByUsername(String username) {
		if (repository.existsByUsername(username)) {
			repository.deleteByUsername(username);
		} else if (!repository.existsByUsername(username)) {
			throw new CustomException("User does not Exist", HttpStatus.NOT_FOUND);
		} else {
			throw new CustomException("Something went wrong! Please try again later.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}
}
