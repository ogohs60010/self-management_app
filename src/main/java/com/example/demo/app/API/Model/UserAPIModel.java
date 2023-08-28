package com.example.demo.app.API;

import java.util.Map;

import lombok.Data;




@Data
public class UserAPIModel {
	private String user;
	private Map<String,Map<String,Integer>> item;
	UserAPIModel(String user,Map<String,Map<String,Integer>> item){
		this.user=user;
		this.item=item;
	}
}
