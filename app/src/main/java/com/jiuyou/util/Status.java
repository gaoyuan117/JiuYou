package com.jiuyou.util;

public class Status {

	public enum LoginStatus{
		REGISTED, PWD_IS_WRONG, PHONE_NOT_EXIST
		, CODE_IS_WRONG, PWD_FORMAT_WRONG, PWD_IS_NULL
		, PHONE_IS_NULL, PWD_IS_RIGHT, PHONE_AND_PWD_IS_RIGHT
		,PHONE_FORMAT_WRONG,SECOND_PASSWORD_IS_NULL,TWO_PWD_NOT_EQUALS
		,CODE_IS_NULL,NETWORK_IS_NOT_AVALIABLE,PARAM_IS_WRONG,CODE_IS_OVERDUE,UUID_IS_NULL
		,MSG_OVER_FIVE,SEND_FAIL,REQUEST_FAIL
	}
	
	
	public enum MineStatus{
		PARAM_IS_WRONG,USER_NOT_EXIST,UPLOAD_FAIL,NETWORK_IS_NOT_AVALIABLE,GET_PHOTO_FAIL
	}
	
	
	
	
}
