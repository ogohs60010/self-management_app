package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.app.appdb.Dao.NumericalNameDao;
import com.example.demo.app.appdb.NumName.ListNum;
import com.example.demo.app.appdb.NumName.NumericalName;
@Controller
@RequestMapping("/app/initInputpage")
public class initInputpageController {
    @Autowired
    private NumericalNameDao dao;

    @GetMapping
    public String userPageInput(@AuthenticationPrincipal UserDetails user, Model model) {
        String userName = user.getUsername();
        ListNum userData = new ListNum();
        userData.setListNumName( dao.findUser(userName));

        model.addAttribute("title", userName+" Data Input");
        model.addAttribute("msg", "こんにちは" + userName + "さん");
        model.addAttribute("NumList", userData);

        return "initInputpage";
    }

    @PostMapping("/input")
    public String userPageInputForm(
            @AuthenticationPrincipal UserDetails user,
            @ModelAttribute("NumList") ListNum numericalNameList,
            @RequestParam("new_name") String newName,
            @RequestParam("new_min") String newMin,
            @RequestParam("new_max") String newMax) {
        String userName = user.getUsername();
        // 既存のデータの更新
        try {
        	for (NumericalName numericalName : numericalNameList.getListNumName()) {
                
            	dao.update(numericalName.getId(), userName, numericalName.getNumName(), numericalName.getMin(), numericalName.getMax());
            }	
        }catch(NullPointerException e){
        	
        }
        
        // 新しいデータの作成
        if (!newName.isEmpty() && !newMin.isEmpty() && !newMax.isEmpty()) {
            dao.create(userName, newName, newMin, newMax);
        }

        return "redirect:/app/initInputpage";
    }
    
    @PostMapping("/delete")
    public String DeleteForm(
    		@AuthenticationPrincipal UserDetails user,
    		@RequestParam("deleteId") String deleteId) {
    	String userName = user.getUsername();
    	dao.delete(userName,deleteId);
    	
    	return "redirect:/app/initInputpage";
    	
    }
}