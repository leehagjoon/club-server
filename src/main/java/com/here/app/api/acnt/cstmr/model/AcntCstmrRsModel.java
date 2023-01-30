package com.here.app.api.acnt.cstmr.model;

import lombok.Data;

@Data
public class AcntCstmrRsModel {
	
	private int errCode; // 1 : 성공 ,  -1 : 가입된 ID 존재 , -2 : 동일한 이메일 존재 , -3 동일한 휴대폰번호 존재 
	
	private String loginId; // 가입 성공된 ID 전달
	
	
}
