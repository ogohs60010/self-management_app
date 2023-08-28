package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.app.algorithm.FenwickTree;
import com.example.demo.app.algorithm.DI_in_algorithm.DIBitAddmin;
import com.example.demo.app.appdb.Dao.MenberDao;
import com.example.demo.app.appdb.Dao.NumericalNameDao;
import com.example.demo.app.appdb.Dao.NumericalaManagementDao;
import com.example.demo.app.appdb.NumName.NumericalName;
import com.example.demo.app.appdb.NumericalManagement.NumericalManagement;


@Controller
@RequestMapping("/appmenu")
public class AppMenu {
	@Autowired
	private DIBitAddmin BITSave;
    @Autowired
    private NumericalNameDao namedao;
    @Autowired
    private NumericalaManagementDao Managerdao;
    @Autowired
    private MenberDao menberdao;
    
    public FenwickTree GetFenwickTree(String numname,String user) {
    	FenwickTree BITreturn = BITSave.get_BIT_instance(numname, user);
    	return BITreturn;
    }

	@GetMapping
	public ModelAndView AppMenuGet(@AuthenticationPrincipal UserDetails user,ModelAndView
			mav) {
		String userName = user.getUsername();
		String userrole = user.getAuthorities().toString();
		mav.setViewName("appmenu.html");
		if(userrole.equals("[ROLE_ADMIN]")) {
			mav.addObject("login_user", false);
			mav.addObject("login",true);	
		   }else if (userrole.equals("[ROLE_USER]")){
				ArrayList<NumericalName> InitName = namedao.findUser(userName);
				ArrayList<Integer> IntArray = new ArrayList<>();
				for(NumericalName name:InitName) {
					String NumName = name.getNumName();
					List<NumericalManagement> numerical = Managerdao.findByUserAndNumName(userName, NumName);
					for(NumericalManagement data:numerical) {
						Integer i = Integer.parseInt(data.getNumInit());
						IntArray.add(i);
					}
					BITSave.set_BIT_instance(IntArray, name.getNumName(), userName);
				}
			   mav.addObject("login_user", true);
			   mav.addObject("login",false);
		   }

		return mav;
	}

}
