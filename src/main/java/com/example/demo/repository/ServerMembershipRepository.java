package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ServerMembership;
import com.example.demo.model.ServerMembershipKey;

@Repository
public interface ServerMembershipRepository extends CassandraRepository<ServerMembership, ServerMembershipKey> {
	
	//Find all members of a server
	List<ServerMembership> findByServerMembershipKeyServerId(UUID serverId);
	
	//Find all servers a user belongs 
	List<ServerMembership> findByServerMembershipKeyUserId(UUID userId);
	
	boolean existsByServerMembershipKeyServerId(UUID serverId);
}
