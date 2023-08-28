package com.example.demo.app.appdb.NumericalManagement;

import lombok.Data;

@Data
public class NumericalManagement {
	private String id;
	private String date;
	private String NumInit;
	private String NumName;
	private String min;
	private String max;
	public NumericalManagement(String id,String NumName,String date ,String NumInit) {
		this.NumName=NumName;
		this.id = id;
		this.date = date;
		this.NumInit = NumInit;
	}
	public NumericalManagement() {}
	
}
