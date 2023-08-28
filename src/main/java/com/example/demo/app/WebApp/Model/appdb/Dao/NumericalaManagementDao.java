package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.app.appdb.NumericalManagement.NumericalManagement;

@Component
@Repository
public class NumericalaManagementDao {
	@Autowired
    private JdbcTemplate template;

    public List<NumericalManagement> findByUser(String user) {
    	String sql = "SELECT * FROM numerical_management_"+user+" ;";
    	List<NumericalManagement> list = new ArrayList<>();
    	
    	try {
    		SqlRowSet rs = template.queryForRowSet(sql);
    		while(rs.next()) {
    			NumericalManagement numericalManagi = new NumericalManagement(
    					rs.getString("id"),
    					rs.getString("num_name"),
    					rs.getString("management_date"),
    					rs.getString("num_init")
    			);
    			list.add(numericalManagi);
    		}
    	} catch (EmptyResultDataAccessException e) {
      	  // 結果がない場合、空のリストを返す
      	        return list;	
      	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    public List<NumericalManagement> findByUserAndDate(String user,String date){
    	String sql = "SELECT * FROM numerical_management_"+user+" WHERE management_date = ?";
    	List<NumericalManagement> list = new ArrayList<>();
    	
    	try {
    		SqlRowSet rs = template.queryForRowSet(sql,date);
    		while(rs.next()) {
    			NumericalManagement numericalManagi = new NumericalManagement(
    					rs.getString("id"),
    					rs.getString("num_name"),
    					rs.getString("management_date"),
    					rs.getString("num_init")
    			);
    			list.add(numericalManagi);
    		}
    	} catch (EmptyResultDataAccessException e) {
    	  // 結果がない場合、空のリストを返す
    	        return list;	
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    public List<NumericalManagement> findByUserAndNumName(String user,String NumName){
    	String sql = "SELECT * FROM numerical_management_"+user+" WHERE num_name = ? ORDER BY management_date";
    	List<NumericalManagement> list = new ArrayList<>();
    	
    	try {
    		SqlRowSet rs = template.queryForRowSet(sql,NumName);
    		while(rs.next()) {
    			NumericalManagement numericalManagi = new NumericalManagement(
    					rs.getString("id"),
    					rs.getString("num_name"),
    					rs.getString("management_date"),
    					rs.getString("num_init")
    			);
    			list.add(numericalManagi);
    		}
    	} catch (EmptyResultDataAccessException e) {
    	  // 結果がない場合、空のリストを返す
    	        return list;	
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    public List<NumericalManagement> findInit(String user,String date,String NumName){
    	String sql = "SELECT * FROM numerical_management_"+user+" WHERE management_date = ? AND num_name = ?;";
    	List<NumericalManagement> list = new ArrayList<>();
    	
    	try {
    		SqlRowSet rs = template.queryForRowSet(sql,date,NumName);
    		while(rs.next()) {
    			NumericalManagement numericalManagi = new NumericalManagement(
    					rs.getString("id"),
    					rs.getString("num_name"),
    					rs.getString("management_date"),
    					rs.getString("num_init")
    			);
    			list.add(numericalManagi);
    		}
    	} catch (EmptyResultDataAccessException e) {
      	  // 結果がない場合、空のリストを返す
      	        return list;	
      	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    public List<NumericalManagement> findByUserAndSort(String user) {
    	String sql = "SELECT * FROM numerical_management_"+user+" ORDER BY management_date,num_name ;";
    	List<NumericalManagement> list = new ArrayList<>();
    	
    	try {
    		SqlRowSet rs = template.queryForRowSet(sql);
    		while(rs.next()) {
    			NumericalManagement numericalManagi = new NumericalManagement(
    					rs.getString("id"),
    					rs.getString("num_name"),
    					rs.getString("management_date"),
    					rs.getString("num_init")
    			);
    			list.add(numericalManagi);
    		}
    	} catch (EmptyResultDataAccessException e) {
      	  // 結果がない場合、空のリストを返す
      	        return list;	
      	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    public void update(String username,String date,String num_name,String num_init) {
    	String sql = "update numerical_management_"+username +" set num_init = ? WHERE management_date = ? AND num_name =? ";
    	template.update(sql,
    			num_init,
    			date,
    			num_name);
    }
    public void create(String user, String date, String NumName, String NumInit) {
        String sql = "INSERT INTO numerical_management_" + user + " (management_date, num_name, num_init) VALUES (?, ?, ?)";

        template.update(sql, date, NumName, NumInit);
    }

}
