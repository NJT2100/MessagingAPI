package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.model.ServerMembership;
import com.example.demo.model.ServerMembershipKey;
import com.example.demo.model.ServerRole;
import com.example.demo.repository.ServerMembershipRepository;
import com.example.demo.util.CustomException;

@Service
public class ServerMembershipService {
	
	@Autowired
	private ServerMembershipRepository serverMembershipRepo;
	
	public void addMember(UUID serverID, UUID userID, String servername, String username, List<ServerRole> roles) 
		throws CustomException {
		if (serverMembershipRepo.existsById(new ServerMembershipKey(serverID, userID)))
			throw new CustomException("User is already a member", HttpStatus.BAD_REQUEST);
		ServerMembershipKey membershipKey = new ServerMembershipKey();
		membershipKey.setServerId(serverID);
		membershipKey.setUserId(userID);
		
		ServerMembership membership = new ServerMembership();
		membership.setServerMembershipKey(membershipKey);
		membership.setAlias(null);
		membership.setServername(servername);
		membership.setUsername(username);
		membership.setRoles(roles);
		membership.setJoined(new Date());
		serverMembershipRepo.save(membership);
	}
	
	public void deleteMember(UUID serverID, UUID userID) throws CustomException {
		if (!serverMembershipRepo.existsById(new ServerMembershipKey(serverID, userID)))
			throw new CustomException("User is not a member", HttpStatus.BAD_REQUEST);
		ServerMembershipKey key = new ServerMembershipKey(serverID, userID);
		serverMembershipRepo.deleteById(key);
	}
	
	public void deleteAllMembers(UUID serverID) {
		ArrayList<ServerMembership> members = (ArrayList<ServerMembership>) 
				serverMembershipRepo.findByServerMembershipKeyServerId(serverID);
		serverMembershipRepo.deleteAll(members);
	}
	
	public List<ServerMembership> getMembers(UUID serverID) {
		return serverMembershipRepo.findByServerMembershipKeyServerId(serverID);
	}
}
