package com.example.demo.app.appdb.NumericalManagement;

import java.util.List;

import lombok.Data;

@Data
public class ManagementList {
	private List<NumericalManagement> managementList;

	public Boolean serch(String name) {
		for(NumericalManagement numname:this.managementList) {
			if(name.equals(numname.getNumName())) {
				return true;
			}
		}
		return false;
	}
}
