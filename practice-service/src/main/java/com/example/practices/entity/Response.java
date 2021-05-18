package com.example.practices.entity;

import lombok.Data;

@Data
public class Response {

	private boolean result=true;
	private String message="success";
	private Object data;
	
	public Response() {
		
	}
	
	public Response(boolean result, String message, Object data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}
	
	
}
