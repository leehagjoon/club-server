package com.here.app.api.acnt.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor 
@AllArgsConstructor
@Data
public class JwtRqModel implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String userId;
	private String userPswd;
}
