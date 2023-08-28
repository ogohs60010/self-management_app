package com.example.demo.app.appdb.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class RollGetter {
	@Autowired
    private JdbcTemplate template;
	public String Find(String user){
		String sql ="SELECT authority FROM authorities WHERE username = ? ;";
		String authority= new String();
		try{
			SqlRowSet rs = template.queryForRowSet(sql,user);
			while(rs.next()) {
				authority = rs.getString("authority");
			}
		
		}catch(EmptyResultDataAccessException e) {
			String none="";
			return none;
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(authority);
		return authority;
	}

}
