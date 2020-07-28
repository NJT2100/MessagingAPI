package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ServerRole;
import com.example.demo.model.User;
import com.example.demo.service.ChannelService;
import com.example.demo.service.ServerGroupService;
import com.example.demo.service.ServerMembershipService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/api/server")
@RestController
public class ServerGroupController {
	
	@Autowired
	private ServerGroupService serverService;
	
	@Autowired
	private ServerMembershipService membershipService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService channelService;
	
	@PostMapping(value="")
	public ResponseEntity<String> createServer(HttpServletRequest req, HttpServletResponse res) {
		try {
			String jsonPostData = req.getReader().lines().collect(Collectors.joining());
			ObjectMapper objectMapper = new ObjectMapper();
			HashMap<String, String> map = objectMapper.readValue(jsonPostData,
					new TypeReference<HashMap<String, String>>(){});
			String username = map.get("username");
			String servername = map.get("server_name");
			
			User owner = userService.findByUsername(username);			
			
			UUID serverID = serverService.createServer(owner.getId(), servername);
			
			membershipService.addMember(serverID, owner.getId(), servername,
					username, new ArrayList<ServerRole>(Arrays.asList(ServerRole.ROLE_USER, 
					ServerRole.ROLE_ADMIN, ServerRole.ROLE_OWNER)));
			
			channelService.createChannel(serverID, "general", 
					new ArrayList<ServerRole>(Arrays.asList(ServerRole.ROLE_GUEST, ServerRole.ROLE_USER,
					ServerRole.ROLE_ADMIN, ServerRole.ROLE_OWNER)));
			return ResponseEntity.ok().body("Server " + servername + " created");
		} catch (CustomException cex) {
			return ResponseEntity.status(cex.getHttpStatus()).body(cex.getMessage());
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("");
		} 
	}
	
	@GetMapping(value="/{serverLink}")
	public ResponseEntity<String> getServer(HttpServletResponse res, @PathVariable String serverLink) {
		return null;
	}
	
	@DeleteMapping(value="/{serverLink}")
	public ResponseEntity<String> deleteServer(HttpServletResponse res, @PathVariable String serverLink) {
		return null;
	}
	
	
}
