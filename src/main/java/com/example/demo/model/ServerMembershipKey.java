package com.example.demo.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyClass
public class ServerMembershipKey implements Serializable {

	private static final long serialVersionUID = -2375236244619972794L;

	@PrimaryKeyColumn(name="server_id", ordinal=0, type=PrimaryKeyType.PARTITIONED)
	private UUID serverId;
	
	@PrimaryKeyColumn(name="user_id", ordinal=1, type=PrimaryKeyType.CLUSTERED)
	private UUID userId;
}
