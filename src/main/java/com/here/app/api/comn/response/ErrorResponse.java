package com.here.app.api.comn.response;

import com.palnet.comn.code.RSErrorCode;
import lombok.Data;

@Data
public class ErrorResponse extends BasicResponse{
	
	private String errorMessage;
	private String errorCode;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = "404";
	}
	
	public ErrorResponse(RSErrorCode code) {
		this.errorMessage = code.message();
		this.errorCode = code.code();
	}
	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

}
