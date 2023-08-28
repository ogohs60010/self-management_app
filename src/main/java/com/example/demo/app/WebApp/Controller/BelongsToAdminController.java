package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.appdb.Dao.MenberDao;
import com.example.demo.app.appdb.Dao.usersDao;

@Controller
@RequestMapping("/BelongsTo")
public class BelongsToAdminController {
	@Autowired
	private MenberDao menberdao;
	@Autowired
	private usersDao dao;
	@GetMapping
	 public String showBelongsToFormAdmin(@AuthenticationPrincipal UserDetails user,Model model) {
		String username = user.getUsername();
		List<String> admin=dao.FindAdmin();
		List<String> menber = menberdao.findAdmin(username);
		model.addAttribute("Registr",menber);
		model.addAttribute("admins",admin ); 
		return "Belongs_to_admin";
    }
}
