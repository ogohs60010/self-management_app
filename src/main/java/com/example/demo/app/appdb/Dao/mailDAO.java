package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class mailDAO {
	@Autowired
    private JdbcTemplate template;
	
	public ArrayList<String> addressFind(String user){
		String sql ="SELECT addres FROM user_mail WHERE username = ? ;";
		ArrayList<String> list = new ArrayList<>();
		try{
			SqlRowSet rs = template.queryForRowSet(sql,user);
			while(rs.next()) {
				String mailaddress = rs.getString("address");
				list.add(mailaddress);
			}
		
		}catch(EmptyResultDataAccessException e) {
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void addressAdd (String user,String address) {
		String sql ="INSERT INTO user_mail  (username, address) VALUES (?,?)";
		template.update(sql,user,address);
	}

}
