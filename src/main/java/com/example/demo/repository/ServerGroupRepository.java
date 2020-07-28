package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ServerGroup;

@Repository
public interface ServerGroupRepository extends CassandraRepository<ServerGroup, UUID> {
	
	List<ServerGroup> findByOwnerId(UUID ownerID);
	
	ServerGroup findByServerId(UUID serverID);
}
