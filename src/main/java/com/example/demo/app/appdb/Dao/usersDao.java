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
public class usersDao {
	@Autowired
	   private JdbcTemplate template;
	
	public List<String> FindAdmin() {
		String sql = "SELECT username FROM authorities WHERE authority = 'ROLE_ADMIN' ;";
		List<String> list = new ArrayList<>();
		try {
			List<Map<String,Object>> rows = template.queryForList(sql);
			for (Map<String,Object> row : rows) {
				String menber = row.get("username") != null ? row.get("username").toString() : "";
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

}
