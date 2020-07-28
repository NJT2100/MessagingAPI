package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("server_membership")
public class ServerMembership {
	
	@PrimaryKey
	ServerMembershipKey serverMembershipKey;
	
	@Column(value="server_name")
	@Indexed
	private String servername;
	
	@Indexed
	private String username;
	
	private String alias;
	
	private List<ServerRole> roles;
	
	private Date joined;
}