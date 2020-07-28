package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.demo.model.ServerGroup;
import com.example.demo.repository.ServerGroupRepository;
import com.example.demo.util.CustomException;

@Service
public class ServerGroupService {
	
	@Autowired
	private ServerGroupRepository serverRepo;
	
	public UUID createServer(UUID ownerID, String serverName) throws CustomException {
		if (serverName == null || serverName.equals(""))
			throw new CustomException("Server must be named", HttpStatus.BAD_REQUEST);	
		ArrayList<ServerGroup> userServers = (ArrayList<ServerGroup>) serverRepo.findByOwnerId(ownerID);
		for (ServerGroup server : userServers ) {
			if (server.getServerName().equals(serverName))
				throw new CustomException("User has aleady created server " + serverName, HttpStatus.BAD_REQUEST);
		}
		UUID serverID = Uuids.timeBased();
		String serverLink = genServerLink();
		ServerGroup server = new ServerGroup(serverID, ownerID, serverName, serverLink, new Date());
		serverRepo.save(server);
		return serverID;
	}
	
	public void deleteServer(UUID serverID) throws CustomException {
		if (!serverRepo.existsById(serverID))
			throw new CustomException("Server does not exist", HttpStatus.BAD_REQUEST);
		serverRepo.deleteById(serverID);
	}
	
	public ServerGroup getServerByID(UUID serverID) throws CustomException {
		if (!serverRepo.existsById(serverID))
			throw new CustomException("Server does not exist", HttpStatus.BAD_REQUEST);
		return serverRepo.findByServerId(serverID);
	}
	
	public List<ServerGroup> getServersByOwnerID(UUID userID) {
		return serverRepo.findByOwnerId(userID);
	}
	
	public void renameServer(UUID serverID, String servername) throws CustomException {
		if (!serverRepo.existsById(serverID))
			throw new CustomException("Server does not exist", HttpStatus.BAD_REQUEST);
		ServerGroup server = serverRepo.findByServerId(serverID);
		server.setServerName(servername);
		serverRepo.save(server);
	}
	
	private String genServerLink() {
		StringBuilder link = new StringBuilder();
		int length = 18;
		for (int i = 0; i < length; i++) {
			long digit = Math.round(Math.random() * 9);
			link.append(digit);
		}
		return link.toString();
	}
}
