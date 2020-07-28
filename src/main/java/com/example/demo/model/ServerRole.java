package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum ServerRole implements GrantedAuthority {
	ROLE_GUEST, ROLE_USER, ROLE_ADMIN, ROLE_OWNER;
	
	@Override
	public String getAuthority() {
		return name();
	}

}
