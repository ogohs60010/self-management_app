package com.example.demo.Controller;

import java.util.Calendar;

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
import com.example.demo.app.appdb.Dao.NumericalaManagementDao;
import com.example.demo.app.appdb.NumName.ListNum;
import com.example.demo.app.appdb.NumName.NumericalName;
import com.example.demo.app.appdb.NumericalManagement.ManagementList;
import com.example.demo.app.appdb.NumericalManagement.NumericalManagement;


@Controller
@RequestMapping("/app/PhysicalConditionInput")
public class PhysicalConditionInputController {
    Calendar cal = Calendar.getInstance();
    String now=String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) +1, cal.get(Calendar.DATE));
    String dateTime = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) +1, cal.get(Calendar.DATE));
    String massage;
    
    @Autowired
    private NumericalNameDao namedao;
    @Autowired
    private NumericalaManagementDao Managerdao;
    
    @GetMapping
    public String userPageInput(@AuthenticationPrincipal UserDetails user, Model model) {
        String userName = user.getUsername();
        ManagementList UserData = new ManagementList();
        ListNum numericalNameList = new ListNum();
        UserData.setManagementList(Managerdao.findByUserAndDate(userName, this.dateTime));
        numericalNameList.setListNumName(namedao.findUser(userName));

        for (NumericalName data : numericalNameList.getListNumName()) {
            if (!UserData.serch(data.getNumName())) {
                Managerdao.create(userName, this.dateTime, data.getNumName(), data.getMin());
            }
        }
        UserData.setManagementList(Managerdao.findByUserAndDate(userName, this.dateTime));
        for(NumericalManagement data:UserData.getManagementList()) {
        	data.setMin(numericalNameList.serchMin(data.getNumName()));
        	data.setMax(numericalNameList.serchMax(data.getNumName()));
        }
        model.addAttribute("now",this.now);
        model.addAttribute("title",userName+" Condition Input");
        model.addAttribute("dateTime", this.dateTime);
        model.addAttribute("ManagementList", UserData);
        model.addAttribute("sendmessage", this.massage);
        return "PhysicalConditionInput";
    }

    @PostMapping("/send")
    public String userPageInputForm(@AuthenticationPrincipal UserDetails user, Model model,
                                    @ModelAttribute("ManagementList") ManagementList numericalManagementList
                                   ) {
        String userName = user.getUsername();
        for (NumericalManagement data : numericalManagementList.getManagementList()) {
            Managerdao.update(userName, this.dateTime, data.getNumName(), data.getNumInit());
        }
        this.massage= this.dateTime + "のフォームを送信しました。";
        
        return "redirect:/app/PhysicalConditionInput";
    }

    @PostMapping("/decide")
    public String sendForm(@AuthenticationPrincipal UserDetails user, Model model,
                           @RequestParam("serch_date") String dateTime) {
        this.dateTime = dateTime;
        massage="";
        return "redirect:/app/PhysicalConditionInput";
    
    }
}

