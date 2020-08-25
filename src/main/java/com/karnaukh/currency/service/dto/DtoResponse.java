package com.karnaukh.currency.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DtoResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS dd-MM-yyyy")
	private Timestamp timestamp;

	private String message;

	private Integer status;

	private String path;

	public DtoResponse(String message) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.message = message;
	}

	public DtoResponse(String message, int status, String path) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.message = message;
		this.status = status;
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
