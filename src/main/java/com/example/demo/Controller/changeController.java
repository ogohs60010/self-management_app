package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/change")
public class changeController {
	private String user;
	private String role;
	   @Autowired
	    private UserDetailsManager userDetailsManager;
	   @Autowired
	    PasswordEncoder passwordEncoder;
	@GetMapping
	public String changePageInput(@AuthenticationPrincipal UserDetails user, Model model) {
		String userName = user.getUsername();
		String roll=user.getAuthorities().toString();
		return "/passchange";
	}
	@PostMapping
	public String changepass(String oldPassword,String newPassword,Model model) {
		if (oldPassword.equals(newPassword)){
			model.addAttribute("message", "同じパスワードを入力していました。");
		}else {
			try {
				String nextPassword=passwordEncoder.encode(newPassword);
				userDetailsManager.changePassword(oldPassword, nextPassword);
			}catch(Exception e) {
				model.addAttribute("message",e.getLocalizedMessage());
			}
		}
		return "redirect:/change";
	}

}
