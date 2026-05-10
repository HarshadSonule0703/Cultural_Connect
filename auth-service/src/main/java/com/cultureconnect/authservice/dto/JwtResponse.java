package com.cultureconnect.authservice.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String jwttoken;
	private final String email;

	public JwtResponse(String jwttoken,String email) {
		this.jwttoken = jwttoken;
		this.email = email;
	}

	public String getToken() {
		return this.jwttoken;
	}
	public String getEmail() {
		return this.email;
	}
}
