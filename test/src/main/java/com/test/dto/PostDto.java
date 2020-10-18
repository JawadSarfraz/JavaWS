package com.test.dto;

import java.util.Date;
import java.io.Serializable;

public class PostDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;

	private String description;
	private String status;
	private Date timeStamp;
	private Integer likes; // Later it will be List of unique users who like it
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

}
