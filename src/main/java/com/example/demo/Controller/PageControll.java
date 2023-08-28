package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.app.appdb.NewsTopics;
import com.example.demo.app.appdb.Dao.NewsTopicsDao;
import com.example.demo.app.appdb.Dao.NumericalaManagementDao;
import com.example.demo.app.appdb.Dao.usersDao;

@Controller
public class PageControll {
    @Autowired
    private NumericalaManagementDao Managerdao;
	@RequestMapping("/self")
	public ModelAndView selfintroduction(ModelAndView mav) {
		mav.setViewName("appselfintroduction.html");
		return mav;
	}
	@Autowired
	NewsTopicsDao dao;
	@Autowired
	usersDao userdao;
	@RequestMapping("/")
	public ModelAndView home(ModelAndView mav) {  
		List<NewsTopics> topics = dao.findAll();
		   mav.addObject("topics", topics);  // Add the topics to the model
		   mav.setViewName("index.html");
		
		return mav;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mav,
			@RequestParam(value="error",required=false)String error) {
		mav.setViewName("login");
		if(error != null) {
			mav.addObject("msg","ログインできませんでした。");
		}else {
			mav.addObject("msg","ユーザー名とパスワードを入力：");
		}
		return mav;
	}
	@RequestMapping("")
	public ModelAndView space(ModelAndView mav) {
		mav.setViewName("space");
		return mav;
	}
}
