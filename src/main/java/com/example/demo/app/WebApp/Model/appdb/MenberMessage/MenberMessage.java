package com.example.demo.app.appdb.MenberMessage;

import lombok.Data;

@Data
public class MenberMessage {
	private String user;
	private String message;
	private String Date;
	public MenberMessage(String user,String message,String Date) {
		this.user=user;
		this.message=message;
		this.Date=Date;
	}
	public MenberMessage() {}
	
}
