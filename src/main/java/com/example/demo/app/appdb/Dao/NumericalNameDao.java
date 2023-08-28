package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.app.appdb.NumName.NumericalName;

@Component
@Repository
public class NumericalNameDao {
    @Autowired
    private JdbcTemplate template;

    public ArrayList<NumericalName> findUser(String user) {
        String sql = "SELECT * FROM numerical_name_"+user+" ;";
        ArrayList<NumericalName> list = new ArrayList<>();

        try {
        	List<Map<String, Object>> rows = template.queryForList(sql);
            for (Map<String, Object> row : rows) {
                String id = row.get("id") != null ? row.get("id").toString() : "";
                String num_name = row.get("num_name") != null ? row.get("num_name").toString() : "";
                String num_min = row.get("min_num") != null ? row.get("min_num").toString() : "";
                String num_max = row.get("max_num") != null ? row.get("max_num").toString() : "";
                NumericalName name = new NumericalName(id, num_name, num_min, num_max);
                list.add(name);
            }
        } catch (EmptyResultDataAccessException e) {
      	  // 結果がない場合、空のリストを返す
      	        return list;	
      	
        } catch (Exception e) {
            // エラーハンドリングの処理を追加することが推奨されます
            e.printStackTrace();
        }
        

        return list;
    }
    public void update(String id, String user, String num_name, String min_num, String max_num) {
    	String sql = "UPDATE numerical_name_" + user + " SET num_name = ?, min_num = ?, max_num = ? WHERE id = ?";
        template.update(sql, num_name, min_num, max_num, id);
    }

    public void create(String user, String num_name, String min_num, String max_num) {
    	String sql = "INSERT INTO numerical_name_" + user + " (num_name, min_num, max_num) VALUES (?, ?, ?)";
        template.update(sql, num_name, min_num, max_num);
    }
    public void delete(String user,String id) {
    	int AutoIncrement=Id_sGet(user).size();
    	int id_number = Integer.parseInt(id);
    	String delete_sql = "DELETE FROM numerical_name_" + user + " WHERE id = ?;";
        template.update(delete_sql,id);
        for (int i = id_number + 1; i <= AutoIncrement; ++i) {
            String idUpdateSql = "UPDATE numerical_name_" + user + " SET id = ? WHERE id = ?;";
            template.update(idUpdateSql, i - 1, i);
        }
    	String AutoIncrementSql="ALTER TABLE numerical_name_" + user + " AUTO_INCREMENT = ? ;"; 
    	template.update(AutoIncrementSql,AutoIncrement);
    }
    private List<String> Id_sGet(String user) {
    	String sql = "SELECT id FROM numerical_name_"+user+";";
    	ArrayList<String> Id_List = new ArrayList<>();
    	try {
    		List<Map<String, Object>> rows = template.queryForList(sql);
    		for (Map<String, Object> row : rows) {
                String Id = row.get("id") != null ? row.get("id").toString() : "";
                Id_List.add(Id);
    		}
    	} catch (Exception e) {
        // エラーハンドリングの処理を追加することが推奨されます
        e.printStackTrace();
    	}
    	return Id_List;
    }
}

