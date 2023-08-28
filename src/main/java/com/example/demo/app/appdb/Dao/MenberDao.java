package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class MenberDao {
	  @Autowired
	   private JdbcTemplate template;
	  
	  public List<String> findMenber(String admin){
		  String sql = "SELECT member FROM team_member WHERE admin= ? ;";
		  List<String> list = new ArrayList<>();
		  try {
			List<Map<String,Object>> rows = template.queryForList(sql,admin);
			for (Map<String, Object> row : rows) {
				String menber = row.get("member") != null ? row.get("member").toString() : "";
				String topic = menber;
				list.add(topic);
			}
		  } catch (EmptyResultDataAccessException e) {
			  return list;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  
		  return list;
	  }
	  public List<String> findAdmin(String user){
		  String sql = "SELECT admin FROM team_member WHERE member= ? ;";
		  List<String> list = new ArrayList<>();
		  try {
			List<Map<String,Object>> rows = template.queryForList(sql,user);
			for (Map<String, Object> row : rows) {
				String menber = row.get("admin") != null ? row.get("admin").toString() : "";
				String topic = menber;
				list.add(topic);
			}
		  } catch (EmptyResultDataAccessException e) {
			  return list;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  
		  return list;
	  }
	  public void update(String admin,String user){
		  String sql ="INSERT INTO team_member (admin,member) VALUES (?,?)";
		  template.update(sql,admin,user);
	  }
	  public void delete(String admin,String user) {
		  String sql ="DELETE FROM team_member WHERE member= ? AND admin= ?";
		  template.update(sql,user,admin);
	    }
}
