package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class User {

	@PrimaryKey
	@NonNull
	private UUID id;
	
	@NonNull
	private String email;
	
	@NonNull
	private String username;
	
	@NonNull
	private String password;
	
	@NonNull
	List<Role> roles;
	
	@NonNull
	private Date createdOn;
	
	@NonNull
	private Date modified;
	
	private Date lastLogin;
	
}
