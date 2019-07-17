package com.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String Title;
	private String Text;

//	@Basic
//	@Temporal(TemporalType.DATE)
	private Date created_at;
	

	public Message() {
		
	}
	
	public Message(String title, String text) {
		Title = title;
		Text = text;
		created_at = new Date();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at() {
		//this.created_at = new LocalDateTime().toDate();
		this.created_at = new Date();
	}	
}
