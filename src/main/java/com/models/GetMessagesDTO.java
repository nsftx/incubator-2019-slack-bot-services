package com.models;

import java.util.List;

import com.domain.Message;

public class GetMessagesDTO {
	private List<Message> messages;
	private Integer size;
	private Integer page;
	
	public GetMessagesDTO() {

	}
	
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}	
}
