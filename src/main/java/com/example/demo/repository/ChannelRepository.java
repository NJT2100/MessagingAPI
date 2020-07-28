package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.example.demo.model.Channel;

public interface ChannelRepository extends CassandraRepository<Channel, UUID> {

	List<Channel> findAllByServerId(UUID serverID); 
	
	Channel findByChannelId(UUID channelID);
	
	boolean  existsByServerId(UUID serverID);
}
