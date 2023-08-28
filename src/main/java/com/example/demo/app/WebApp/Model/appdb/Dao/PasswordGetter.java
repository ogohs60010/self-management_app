package com.example.demo.app.appdb.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class PasswordGetter {
	@Autowired
    private JdbcTemplate template;
	public String Find(String user){
		String sql ="SELECT password FROM users WHERE username = ? ;";
		String password= new String();
		try{
			SqlRowSet rs = template.queryForRowSet(sql,user);
			while(rs.next()) {
				password = rs.getString("password");
			}
		
		}catch(EmptyResultDataAccessException e) {
			String none="";
			return none;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}
}
