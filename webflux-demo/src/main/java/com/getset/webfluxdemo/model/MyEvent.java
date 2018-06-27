package com.getset.webfluxdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "event")
public class MyEvent {
	
	
	
    public MyEvent() {
		super();
	}
	public MyEvent(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	@Id
    private String id;
    private String message;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
