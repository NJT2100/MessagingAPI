package com.example.demo.model;

import java.util.Date;
import java.util.UUID;

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
@Table("server_group")
public class ServerGroup {
	
	@PrimaryKey
	@Column(value="server_id")
	private UUID serverId;
	
	@Column(value="owner_id")
	@Indexed(value="idx_owner_id")
	private UUID ownerId;
	
	@Column(value="server_name")
	@Indexed(value="idx_server_name")
	private String serverName;
	
	@Column(value="server_link")
	private String serverLink;
	
	@Column(value="created_on")
	private Date createdOn;
	
}
