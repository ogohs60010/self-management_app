package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.app.AggregateObjectsForGraphs;
import com.example.demo.app.appdb.Dao.MemberMessageDao;
import com.example.demo.app.appdb.Dao.MenberDao;
import com.example.demo.app.appdb.Dao.NumericalNameDao;
import com.example.demo.app.appdb.Dao.NumericalaManagementDao;
import com.example.demo.app.appdb.MenberMessage.MenberMessage;
import com.example.demo.app.appdb.NumName.NumericalName;
import com.example.demo.app.appdb.NumericalManagement.NumericalManagement;

@Controller
@RequestMapping("/admin_controller")
public class ControllerAdminControler {
    Calendar cal = Calendar.getInstance();
    String year;
    String month;
    List<String> SetNumName = new ArrayList<>();
    String tag="";
    int id;
    String user;
    String message;

    @Autowired
    private NumericalNameDao namedao;
    @Autowired
    private NumericalaManagementDao Managerdao;
    @Autowired
    private MenberDao menberdao;
    @Autowired
    private MemberMessageDao messagedao;


	
	@GetMapping
	public String showRegistrationForm(@AuthenticationPrincipal UserDetails admin,Model model) {
		String admin_name = admin.getUsername();
		List<String> Users =new ArrayList<>();
		Users.add(null);
		Users.addAll(menberdao.findMenber(admin_name));
		this.SetNumName = new ArrayList<>();
    	this.SetNumName.add("総合");
    	Optional<String> wrap_user = Optional.ofNullable(this.user);
    	
    	if(wrap_user.isPresent()) {
            List<NumericalName> NumNameList = namedao.findUser(this.user);
            List<AggregateObjectsForGraphs> Graphs = new ArrayList<>();
            for (NumericalName data : NumNameList) {
                this.SetNumName.add(data.getNumName());
                AggregateObjectsForGraphs Graph = new AggregateObjectsForGraphs();
                Graph.setNumName(data.getNumName());
                Graphs.add(Graph);
            }
            List<NumericalManagement> DataList = Managerdao.findByUserAndSort(wrap_user.get());
            for (NumericalManagement data : DataList) {
                for (AggregateObjectsForGraphs Graph : Graphs) {
                    if (Graph.getNumName().equals(data.getNumName())) {
                        Graph.addDateList(data.getDate());
                        Graph.addIntList(Integer.parseInt(data.getNumInit()));
                    }
                }
            }
            
            model.addAttribute("variableList", this.SetNumName);
            model.addAttribute("NumName", this.SetNumName.get(id));
            int max=0;
            for(NumericalName NumName:NumNameList) {
            	if(max<Integer.parseInt(NumName.getMax())) {
                	max=Integer.parseInt(NumName.getMax());        		
            	}
            	if(NumName.getNumName()==this.SetNumName.get(this.id)) {
            		model.addAttribute("max", NumName.getMax());
            	}else {
            		model.addAttribute("max",max);
            	}

            }

        	ArrayList<List<Integer>> Graphinit = new ArrayList<List<Integer>>();
        	List<String> NameLIst = new ArrayList<>();

            
            for (AggregateObjectsForGraphs Graph : Graphs) {
                if (tag.equals("year")) {
                    Graph = restriction(Graph, this.year);
                } else if (tag.equals("month")) {
                    Graph = restriction(Graph, this.month);
                }
                
                if(id > 0) {
                	if (Graph.getNumName().equals(this.SetNumName.get(this.id))) {
    	            	Graphinit.add(Graph.getIntList());
    	            	NameLIst.add(Graph.getNumName());
    	                model.addAttribute("Dates", Graph.getDateList());
    	                model.addAttribute("NameList",NameLIst);
    	                model.addAttribute("DataList", Graphinit);
    	            }
                }else if(id == 0) {
                	Graphinit.add(Graph.getIntList());
                	NameLIst.add(Graph.getNumName());
                    model.addAttribute("Dates", Graph.getDateList());
                    model.addAttribute("NameList",NameLIst);
                    model.addAttribute("DataList", Graphinit);            	
                }
                
                
    	            
            }       
            if(this.year != null) {
         	   model.addAttribute("answer",this.year+ this.SetNumName.get(this.id));   
            }else if(this.month != null){
         	   model.addAttribute("answer",this.month+this.SetNumName.get(this.id));
            }else{
         	   model.addAttribute("answer",this.SetNumName.get(this.id));
            }
                		
    	}
    	List<MenberMessage> message_list = messagedao.AdminFind(admin_name);
    	model.addAttribute("messages_output",message_list );
    	model.addAttribute("submit_message", this.message);
    	model.addAttribute("text_user", this.user);
        model.addAttribute("UserList", Users);
        model.addAttribute("now_year", Integer.toString(cal.get(Calendar.YEAR)));
        model.addAttribute("year", this.year);
        model.addAttribute("now_month", String.format("%04d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1));
        model.addAttribute("month", this.month);
        model.addAttribute("title", "Output page(" + admin_name + ")");
        this.message=null;

		
		return "AdminController";
	}
	
	
	
	
	private AggregateObjectsForGraphs restriction(AggregateObjectsForGraphs Graph, String term) {
        AggregateObjectsForGraphs NewGraphs = new AggregateObjectsForGraphs();
        List<String> Date = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        String pattern = term + ".*";
        NewGraphs.setNumName(Graph.getNumName());
        for (int i = 0; i < Graph.getDateList().size(); ++i) {
            if (Pattern.matches(pattern, Graph.getDateList().get(i))) {
                Date.add(Graph.getDateList().get(i));
                list.add(Graph.getIntList().get(i));
            }
        }
        List<String> Dates = new ArrayList<>();
        List<Integer> lists = new ArrayList<>();
        int counta = 0;

        if (this.year != null) {
            for (int month = 1; month <= 12; month++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(this.year), month - 1, 1);
                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                
                for (int day = 1; day <= daysInMonth; day++) {
                    String date = String.format("%04d-%02d-%02d", Integer.parseInt(this.year), month, day);
                    if (Graph.getDateList().contains(date)) {
                        Dates.add(Graph.getDateList().get(counta));
                        lists.add(Graph.getIntList().get(counta));
                        counta++;
                    } else {
                        Dates.add(date);
                        lists.add(null);
                    }
                }
            }
        } else if (this.month != null) {
            Calendar calendar = Calendar.getInstance();
            String[] parts = this.month.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            calendar.set(year, month - 1, 1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            for (int day = 1; day <= daysInMonth; day++) {
                String date = String.format("%04d-%02d-%02d", year, month, day);
                if (Graph.getDateList().contains(date)) {
                    Dates.add(Graph.getDateList().get(counta));
                    lists.add(Graph.getIntList().get(counta));
                    counta++;
                } else {
                    Dates.add(date);
                    lists.add(null);
                }
            }
        }else {
        	Dates=Date;
        	lists=list;
        }

        NewGraphs.setDateList(Dates);
        NewGraphs.setIntList(lists);

        return NewGraphs;
    }
	@PostMapping("/user")
    public String PostOutputUserName(Model model, @RequestParam("user") String user_name) {
	this.user=user_name;
	return "redirect:/admin_controller";
	}
    @PostMapping("/name")
    public String PostOutputNumName(Model model, @RequestParam("variable") String num_name) {
        brakepoint:
        for (int i = 0; i < this.SetNumName.size(); ++i) {
            if (this.SetNumName.get(i).equals(num_name)) {
                this.id = i;
                break brakepoint;
            }
        }
        return "redirect:/admin_controller";
    }
    @PostMapping("/year")
    public String PostOutputYear(Model model,@RequestParam(value = "year") String year) {
    	if(year!="") {    
    		this.tag = "year";
            this.year = year;
            this.month=null;
    	}
            return "redirect:/admin_controller";
     }
    
    @PostMapping("/month")
    public String PostOutputMonth(Model model,@RequestParam(value = "month") String month) {
         if(month!="") {
        	 this.tag = "month";
             this.month = month;
             this.year=null;   
         }
         return "redirect:/admin_controller";
    }
    @PostMapping("/message")
    public String PostOutputMessage
    (Model model,@RequestParam(value = "message") String message,@AuthenticationPrincipal UserDetails admin) {
    	String admin_name = admin.getUsername(); 
    	if(message!=null && message!="" && this.user !=null) {
    		
        	 messagedao.messageAdd(admin_name, this.user, message);
        	 this.message = "送信しました。";
         }
         return "redirect:/admin_controller";
    }    
     

}


