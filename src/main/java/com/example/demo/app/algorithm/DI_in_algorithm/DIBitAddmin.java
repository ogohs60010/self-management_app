package com.example.demo.app.algorithm.DI_in_algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.example.demo.app.algorithm.FenwickTree;

@Component
public class DIBitAddmin {
	static private HashMap<String,HashMap<String,FenwickTree>> BIT_instance_Addmin;
	
	public void	set_BIT_instance(ArrayList<Integer> array,String intname,String user) {
		HashMap<String,FenwickTree> BIT_instance = new HashMap<>();
		BIT_instance.put(intname,new FenwickTree(array));
		BIT_instance_Addmin = new HashMap<>();
		BIT_instance_Addmin.put(user, BIT_instance);
	}
	
	public FenwickTree get_BIT_instance(String intname, String user) {
		HashMap<String,FenwickTree> BIT_instance = new HashMap<>();
		BIT_instance=BIT_instance_Addmin.get(user);
		return BIT_instance.get(intname);
	}
}
