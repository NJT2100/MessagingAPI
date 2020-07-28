package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.demo.model.Channel;
import com.example.demo.model.ServerRole;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.util.CustomException;

@Service
public class ChannelService {
	
	@Autowired
	private ChannelRepository channelRepository;
	
	public UUID createChannel(UUID serverID, String channelName, List<ServerRole> permissions) throws CustomException {
		ArrayList<Channel> channels = (ArrayList<Channel>) channelRepository.findAllByServerId(serverID);
		for (Channel chnl : channels) {
			if (chnl.getChannelName().equals(channelName))
				throw new CustomException("Channel already exists", HttpStatus.BAD_REQUEST);
		}
		UUID channelID = Uuids.timeBased();
		String channelLink = genChannelLink();
		Channel channel = new Channel(channelID, serverID, channelName, channelLink, permissions, new Date());
		channelRepository.save(channel);
		return channelID;
	}
	
	public void deleteChannel(UUID channelID) throws CustomException {
		if (!channelRepository.existsById(channelID)) {
			throw new CustomException("Channel does not exist", HttpStatus.BAD_REQUEST);
		}
		channelRepository.deleteById(channelID);
	}
	
	public void deleteAllChannels(UUID serverID) {
		if (!channelRepository.existsById(serverID)) {
			throw new CustomException("Server does not exist", HttpStatus.BAD_REQUEST);
		}
		ArrayList<Channel> channels = (ArrayList<Channel>) channelRepository.findAllByServerId(serverID);
		channelRepository.deleteAll(channels);
	}
	
	public Channel getChannel(UUID channelID) {
		if (!channelRepository.existsById(channelID))
			throw new CustomException("Channel does not exist", HttpStatus.BAD_REQUEST);
		return channelRepository.findByChannelId(channelID);
	}
	
	private String genChannelLink() {
		StringBuilder link = new StringBuilder();
		int length = 18;
		for (int i = 0; i < length; i++) {
			long digit = Math.round(Math.random() * 9);
			link.append(digit);
		}
		return link.toString();
	}
}
