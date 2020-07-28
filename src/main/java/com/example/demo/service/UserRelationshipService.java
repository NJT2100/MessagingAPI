package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RelationshipStatus;
import com.example.demo.model.UserRelationship;
import com.example.demo.repository.UserRelationshipRepository;
import com.example.demo.util.CustomException;

@Service
public class UserRelationshipService {

	@Autowired
	private UserRelationshipRepository userRelationshipRepo;
	
	public void createRelationship(UUID user1ID, UUID user2ID, RelationshipStatus status) throws CustomException {
		
	}
	
	public List<UserRelationship> getAllRelationships(UUID userID) {
		ArrayList<UserRelationship> relations1 = 
				(ArrayList<UserRelationship>) userRelationshipRepo.findByUserRelationshipKeyUserOneId(userID);
		ArrayList<UserRelationship> relations2 = 
				(ArrayList<UserRelationship>) userRelationshipRepo.findByUserRelationshipKeyUserOneId(userID);
		relations1.addAll(relations2);
		return relations1;	
	}
	
	public List<UserRelationship> getFriends(UUID userID) {
		ArrayList<UserRelationship> relations = (ArrayList<UserRelationship>) getAllRelationships(userID);
		ArrayList<UserRelationship> friends = new ArrayList<UserRelationship>();
		for (UserRelationship relation : relations) {
			if (relation.getStatus().equals(RelationshipStatus.FRIENDS))
				friends.add(relation);
		}
		return friends;
	}
}
