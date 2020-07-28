package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import lombok.NonNull;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	List<User> findAll();
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);

	void deleteById(@NonNull UUID uuid);
	
	void deleteByUsername(String username);

	boolean existsById(@NonNull UUID uuid);
	
}
