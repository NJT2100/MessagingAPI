package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserRelationship;
import com.example.demo.model.UserRelationshipKey;

@Repository
public interface UserRelationshipRepository extends CassandraRepository<UserRelationship, UserRelationshipKey> {
	
	List<UserRelationship> findByUserRelationshipKeyUserOneId(UUID user1ID);
	
	List<UserRelationship> findByUserRelationshipKeyUserTwoId(UUID user2ID);
}
