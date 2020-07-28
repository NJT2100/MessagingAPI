package com.example.demo.model;

import java.util.Date;
import java.util.List;
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
@Table("channel")
public class Channel {
	
	@PrimaryKey
	@Column(value="channel_id")
	private UUID channelId;
	
	@Column(value="server_id")
	@Indexed
	private UUID serverId;
	
	@Column(value="channel_name")
	@Indexed
	private String channelName;
	
	@Column(value="channel_link")
	@Indexed
	private String channelLink;
	
	private List<ServerRole> permissions;
	
	@Column(value="created_on")
	private Date createdOn;
}
