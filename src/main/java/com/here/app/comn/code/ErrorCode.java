package com.here.app.comn.code;

public enum ErrorCode {
	SUCCESS("1", "성공"),
	FAIL("-1" , "실패"),
	INTERNAL_SERVER_ERROR("ER500", "내부 시스템 오류"),
	NOT_REGIST_ERROR_CODE("ER501", "등록되지 않은 오류 코드"),
	DATA_NOTFIND("DT001","데이터 를 찾을수 없습니다."),
	DATA_DUPLICATE("DT002","중복 데이터가 존재합니다."),
	DATA_NO("DT003", "데이터 미존재"),
	DB_ERROR("DB001" , "디비 처리중 오류");
	
	

	private final String code;

	private final String message;
	
	private ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}
}
