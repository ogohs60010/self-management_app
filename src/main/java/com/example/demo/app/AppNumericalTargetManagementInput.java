package com.example.demo.app;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/appmenu/NumericalTargetManagementName")
public class AppNumericalTargetManagementInput {
	@GetMapping
	public String showManagementInputForm(Model model) {
		return "NumericalTargetManagementName";
	}
	@PostMapping
	public String showManagementInput(@AuthenticationPrincipal UserDetails user,String initname,Integer[] initmanagent) {
		String username = user.getUsername();
		
		return "NumericalTargetManagementName";
	}
}
