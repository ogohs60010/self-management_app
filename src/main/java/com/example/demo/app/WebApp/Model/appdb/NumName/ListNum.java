package com.example.demo.app.appdb.NumName;

import java.util.List;

import lombok.Data;

@Data
public class ListNum {
	private List<NumericalName> ListNumName;
	
	public Boolean serch(String name) {
		for(NumericalName numname:this.ListNumName) {
			if(name.equals(numname.getNumName())) {
				return true;
			}
		}
		return false;
	}
	public String serchMin(String name) {
		for(NumericalName numname:this.ListNumName) {
			if(name.equals(numname.getNumName())) {
				return numname.getMin();
			}
		}
		return "0";
	}
	public String serchMax(String name) {
		for(NumericalName numname:this.ListNumName) {
			if(name.equals(numname.getNumName())) {
				return numname.getMax();
			}
		}
		return "100";
	}
}
