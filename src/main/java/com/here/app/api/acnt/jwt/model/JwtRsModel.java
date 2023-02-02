package com.here.app.api.acnt.jwt.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRsModel implements Serializable {
	
	private String accessToken ;
	
	private String refreshToken;

	private String userId;
	
	private Integer cstmrSno;
	
	private String auth;
	
	
}
