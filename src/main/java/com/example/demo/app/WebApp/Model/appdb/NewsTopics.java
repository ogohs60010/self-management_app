package com.example.demo.app.appdb;

import java.util.HashMap;

public class NewsTopics {
	private String id;
	private String message;
	private String link;
	private String posting_date;
	public NewsTopics(String id,String message,String link,String posting_date) {
		this.id=id;
		this.message=message;
		this.link=link;
		this.posting_date=posting_date;
	}
	public String MessageGetter() {
		return this.message;
	}
	public String LinkGetter() {
		System.out.println(link);
		return this.link;
	}
	
	public String PostingDateGetter() {
		String[] date = this.posting_date.split("T");
		return date[0];
	}
	
	public HashMap<String,String[]> MessageDataGetter(){
		String[] innerArray = {message,link};
		 HashMap<String, String[]> outputData = new HashMap<>();
		    outputData.put(posting_date, innerArray);
		return outputData;
	}
}
