package com.example.demo.app.API;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.app.appdb.Dao.MenberDao;
import com.example.demo.app.appdb.Dao.NumericalNameDao;
import com.example.demo.app.appdb.Dao.NumericalaManagementDao;
import com.example.demo.app.appdb.Dao.RollGetter;
import com.example.demo.app.appdb.NumName.NumericalName;
import com.example.demo.app.appdb.NumericalManagement.NumericalManagement;

@RestController
public class userAPIclass {
	@Autowired
    private NumericalNameDao namedao;
    @Autowired
    private NumericalaManagementDao Managerdao;
    @Autowired
    private MenberDao menberdao;
    @Autowired
    private RollGetter roll;
    @Autowired
    private CheckUserPassword check;
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/userdata")
    public List<UserAPIModel> getUserData(@RequestBody JsonRequestModel user) {
    	
    	String username = new String();
    	String userrole = new String();
    	if (check.check(user)) {
    		username = user.getUser();
    		userrole =roll.Find(user.getUser());
    	}else {
    		username = "error:Login failed";
    	}
    	List<UserAPIModel> UsersItems = new ArrayList<>();
    	if(userrole.equals("ROLE_USER")) {
			UserAPIModel UserItems = this.APIparts(username);
	    	UsersItems.add(UserItems);
	    	return UsersItems;
    	}else if(userrole.equals("ROLE_ADMIN")) {
    		List<String> datanames = menberdao.findMenber(username);
    		
    		for (String dataname:datanames) {
    			UserAPIModel UserItems = this.APIparts(dataname);
    	    	UsersItems.add(UserItems);
    		}
			return UsersItems;
      	}else {
	    	Map<String,Map<String,Integer>> NameAndData = new HashMap<String,Map<String,Integer>>();
	    	Map<String,Integer> INTERNameAndData = new HashMap<String,Integer>();
	    	INTERNameAndData.put("NONE", null);
	    	NameAndData.put("NONE", INTERNameAndData);
	  
	    	UserAPIModel UserItems = new UserAPIModel(username,NameAndData);
	    	UsersItems.add(UserItems);
	    	return UsersItems;
      	}
    }
	private UserAPIModel APIparts(String username){
			Map<String,Map<String,Integer>> NameAndData = new HashMap<String,Map<String,Integer>>();
		ArrayList<NumericalName> initnames=namedao.findUser(username);
		List<NumericalManagement> managements = Managerdao.findByUser(username);
		ArrayList<String> initname = new ArrayList<String>() ;
		for (NumericalName data:initnames) {
			initname.add(data.getNumName()) ;
		}
		Map<String,Integer> DateAndNumber = new HashMap<String,Integer>();
		for (int i = 0; i < managements.size(); ++i) {
		    for (String name : initname) {
		        if (name.equals(managements.get(i).getNumName())) {
		            DateAndNumber.put(name, Integer.parseInt(managements.get(i).getNumInit()));
		        }
		    }
		    
		    String date = managements.get(i).getDate();
		    
		    if (i == managements.size() - 1 || !managements.get(i).getDate().equals(managements.get(i + 1).getDate())) {
		        NameAndData.put(date, DateAndNumber);
		        DateAndNumber = new HashMap<String, Integer>();
		    }
		}
	UserAPIModel UserItems = new UserAPIModel(username,NameAndData);
	return UserItems;
	}
}


record user(String user,String str_name,List<Integer> data,List<Calendar> date ) {}