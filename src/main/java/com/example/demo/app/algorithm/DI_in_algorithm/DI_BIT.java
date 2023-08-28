package com.example.demo.app.algorithm.DI_in_algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.example.demo.app.algorithm.FenwickTree;

@Component
 public class DI_BIT {
	static private HashMap<String,FenwickTree> BIT_instance;
	
	public void	set_BIT_instance(ArrayList<Integer> array,String intname) {
		BIT_instance.put(intname,new FenwickTree(array));
	}
	
	public FenwickTree get_BIT_instance(String intname) {
		return BIT_instance.get(intname);
	}
}
