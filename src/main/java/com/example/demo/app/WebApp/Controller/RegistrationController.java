package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.appdb.MakeTable.tablemake;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @GetMapping
    public String showRegistrationForm(Model model) {
    	model.addAttribute("title", "Registration Page");
        model.addAttribute("msg", "ユーザー名とパスワードを入力してください");
        return "registration";
    }
    @Autowired
    tablemake createtable;
	@Autowired
	PasswordEncoder passwordEncoder;
    @PostMapping
    public String registerUser(String username, String password ,Model model) {
    	  if (userDetailsManager.userExists(username)) {
    		  model.addAttribute("title","Regist Page");
    		  model.addAttribute("msg", "ユーザー名は既に使用されています");
              return "registration";
          }
    	  else if(password.length()<8) {
    		  model.addAttribute("title","Regist Page");
    		  model.addAttribute("msg", "パスワードは8文字以上で入力ください");
              return "registration";
    	  }
    	  else {
        // ユーザーの作成と保存
        userDetailsManager.createUser( User.builder()
                .username(username)
                .password(passwordEncoder.encode(password).toString())
                .roles("USER")
                .build());
        createtable.makeNumericalManagement(username);
        createtable.makeNumericalName(username);
        return "redirect:/login";
    	  }
    }
}