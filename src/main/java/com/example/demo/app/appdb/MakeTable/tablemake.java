package com.example.demo.app.appdb.MakeTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class tablemake {
    @Autowired
    private JdbcTemplate template;

    public void makeNumericalManagement(String user) {
        String sql = "CREATE TABLE numerical_management_" + user + "(" +
                "id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "management_date DATE NOT NULL," +
                "num_name VARCHAR(255) NOT NULL," +
                "num_init int(11) NOT NULL" +
                ")";
        template.execute(sql);
    }

    public void makeNumericalName(String user) {
        String sql = "CREATE TABLE numerical_name_" + user + "(" +
                "id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "num_name varchar(255) NOT NULL," +
                "min_num int(11) NOT NULL," +
                "max_num int(11) NOT NULL" +
                ")";
        template.execute(sql);
    }
    public void MakeMessage(String User) {
    	String sql = "CREATE TABLE Member_message_"+User+"("+
    			"id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
    			"member varchar(255) NOT NULL,"+
    			"message varchar(255) NOT NULL,"+
    			"created_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"+
    			")";
    	template.execute(sql);
    }
    
}

