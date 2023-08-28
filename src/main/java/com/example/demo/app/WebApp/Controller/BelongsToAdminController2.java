package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.app.appdb.Dao.MenberDao;
import com.example.demo.app.appdb.Dao.usersDao;

@Controller
@RequestMapping("/BelongsTo/{admin}")
public class BelongsToAdminController2 {
	private String msg;
	@Autowired
	private usersDao dao;
	@Autowired
	private MenberDao menberdao;
	@GetMapping
	public ModelAndView BelongsToAdminController2Model(
			@AuthenticationPrincipal UserDetails user,
			@PathVariable("admin") String adminname,
			ModelAndView mav
			){
		this.msg =null;
		String username = user.getUsername();
		mav.setViewName("Belongs_to_admin2");
		List<String> admin=dao.FindAdmin();
		
		System.out.println(admin);
		if(admin.contains(adminname)) {
			List<String> menber= menberdao.findMenber(adminname);
			if(menber.contains(username)) {
				mav.addObject("bool_user", true);
				this.msg = adminname + "には既に登録されています";
			}
			mav.addObject("msg", this.msg);
			mav.addObject("data", adminname);
			mav.addObject("number",menber.size());
		}

		return mav;
	}
	@PostMapping("/Belong")
	public String BelongsToAdminController2PostBelong(
			@AuthenticationPrincipal UserDetails user,
			@PathVariable("admin") String adminname,
			Model model
			){
		String username = user.getUsername();
		List<String> adminnames =dao.FindAdmin();
		if(!(adminnames.contains(username))) {
			menberdao.update(adminname, username);
			this.msg = adminname+"へようこそ";
		}else {
			this.msg = username+"はUSER権限ではありません";
		}
		return "redirect:/BelongsTo/"+adminname;
	}
	@PostMapping("/Delete")
	public String BelongsToAdminController2PostDelete(
			@AuthenticationPrincipal UserDetails user,
			@PathVariable("admin") String adminname,
			Model model
			){
		String username = user.getUsername();
		System.out.println(username);
		this.msg = adminname +"から" + username + "は削除されました。";
		menberdao.delete(adminname, username);
		return "redirect:/BelongsTo/"+adminname;
	}

}
