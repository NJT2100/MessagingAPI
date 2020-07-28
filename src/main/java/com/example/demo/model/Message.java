package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Message {
	
	@PrimaryKey
	@Column(value="message_id")
	private UUID messageId;
	
	@Column(value="user_id")
	private UUID userId;
	
	@Column(value="channel_id")
	private UUID channelId;
	
	private List<UUID> recipients;
	
	private String text;
	
	@Column(value="created_on")
	private Date createdOn;
	
	@Column(value="modified_on")
	private Date modifiedOn;
}
