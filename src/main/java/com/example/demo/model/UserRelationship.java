package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user_relationship")
public class UserRelationship {
	
	@PrimaryKey
	private UserRelationshipKey userRelationshipKey;
	
	private RelationshipStatus status;
}
