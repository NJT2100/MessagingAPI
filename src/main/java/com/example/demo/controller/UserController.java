package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomException;
import com.example.demo.util.CookieUtil;

@RequestMapping(value="/api/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user, HttpServletResponse response) {
		try {
			String token = userService.signup(user);
			Cookie jwtCookie = CookieUtil.createCookie(token);
			response.addCookie(jwtCookie);
			return ResponseEntity.ok().body("Successfully registered");
		} catch (CustomException ex) {
			return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
		try {
			String token = userService.signin(jwtRequest);
			Cookie jwtCookie = CookieUtil.createCookie(token);
			response.addCookie(jwtCookie);
			return ResponseEntity.ok().body("Successfully signed in");
		} catch (CustomException ex) {
			return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username, HttpServletRequest request) {
		try {
			//Need to verify that the path variable matches the authentication header
			String authenticatedUser = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
			if (authenticatedUser.equals(username))
				return ResponseEntity.ok(userService.findByUsername(username));
			else
				return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(new User());
		} catch (CustomException ex){
			return ResponseEntity.badRequest().body(new User());
		}
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable String username,
			HttpServletRequest request) {
		try {
			//Need to verify that the path variable matches the authentication header
			String authenticatedUser = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
			if (authenticatedUser.equals(username))
				return ResponseEntity.ok(userService.updateUser(user, username));
			else
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
		} catch (CustomException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUserByUsername(@PathVariable String username,  HttpServletRequest request) {
		try {
			//Need to verify that the authenticated user is an admin or higher
			String authenticatedUsername = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
			User user = userService.findByUsername(authenticatedUsername);
			if (user.getRoles().contains(Role.ROLE_ADMIN)) {
				// Must delete by primary key
				userService.deleteUserById(userService.findByUsername(username).getId());
				return ResponseEntity.ok().body("Deleted user " + username);
			} else {
				return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
			}
		} catch (CustomException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
}
