package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.app.appdb.MenberMessage.MenberMessage;

@Component
@Repository
public class MemberMessageDao {
	@Autowired
    private JdbcTemplate template;
	
	public List<MenberMessage> AdminFind(String admin){
		String sql = "SELECT * FROM member_message_"+admin+" ;";
		List<MenberMessage> list = new ArrayList<>();
		try {
    		SqlRowSet rs = template.queryForRowSet(sql);
    		while(rs.next()) {
    			MenberMessage MessageManagi = new MenberMessage(
    					rs.getString("member"),
    					rs.getString("message"),
    					rs.getString("created_at")
    					
    					);
    			list.add(MessageManagi);
    			
    		}
		}catch(EmptyResultDataAccessException e) {
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<MenberMessage> FindMessage(String admin,String user){
		String sql = "SELECT message, MAX(created_at) AS max_created_at " +
	             "FROM member_message_" +admin+" "+
	             "WHERE member = ? " +
	             "GROUP BY message " +
	             "ORDER BY max_created_at DESC;";
		List<MenberMessage> list = new ArrayList<>();
		try {
    		SqlRowSet rs = template.queryForRowSet(sql,user);
    		while(rs.next()) {
    			MenberMessage MessageManagi = new MenberMessage(
    					admin,
    					rs.getString("message"),
    					rs.getString("max_created_at")
    					);
    			
    			list.add(MessageManagi);
    			
    		}
		}catch(EmptyResultDataAccessException e) {
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void messageAdd (String admin,String user,String message) {
		String sql ="INSERT INTO Member_message_"+admin+"  (member, message) VALUES (?,?)";
		template.update(sql,user,message);
	}
}	

