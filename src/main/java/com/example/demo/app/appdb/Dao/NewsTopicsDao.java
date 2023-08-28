package com.example.demo.app.appdb.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.app.appdb.NewsTopics;

@Component
@Repository
public class NewsTopicsDao {
    @Autowired
    private JdbcTemplate template;

    public List<NewsTopics> findAll() {
        String sql = "SELECT id, message, link, date FROM news_topics " +
                     "GROUP BY id " +
                     "ORDER BY MAX(date) DESC " +
                     "LIMIT 10;";
        List<NewsTopics> list = new ArrayList<>();

        try {
            List<Map<String, Object>> rows = template.queryForList(sql);
            for (Map<String, Object> row : rows) {
                String id = row.get("id") != null ? row.get("id").toString() : "";
                String message = row.get("message") != null ? row.get("message").toString() : "";
                String link = row.get("link") != null ? row.get("link").toString() : "";
                String date = row.get("date") != null ? row.get("date").toString() : "";

                NewsTopics topic = new NewsTopics(id, message, link, date);
                list.add(topic);
            }
        } catch (EmptyResultDataAccessException e) {
      	  // 結果がない場合、空のリストを返す
      	        return list;	
      	
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}