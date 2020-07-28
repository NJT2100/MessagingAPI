package com.example.demo.util;

import javax.servlet.http.Cookie;

public class CookieUtil {
	
	private static final int validityInSeconds = 3600;
	
	public static Cookie createCookie(String token) {
		Cookie cookie = new Cookie("auth", token);
		cookie.setMaxAge(validityInSeconds);
		cookie.setSecure(true);
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie deleteCookie(Cookie cookie) {
		cookie.setValue(null);
		cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		return cookie;
	}
}
