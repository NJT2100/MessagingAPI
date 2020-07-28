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
public class UserRelationshipKey implements Serializable {

	private static final long serialVersionUID = 912328573990794943L;

	@PrimaryKeyColumn(name="user_1_id", ordinal=0, type=PrimaryKeyType.PARTITIONED)
	private UUID userOneId;
	
	@PrimaryKeyColumn(name="user_2_id", ordinal=1, type=PrimaryKeyType.CLUSTERED)
	private UUID userTwoId;
}
