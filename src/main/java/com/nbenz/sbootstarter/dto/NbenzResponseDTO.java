package com.nbenz.sbootstarter.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class NbenzResponseDTO <T> {

	private int code;
	private T result;

	public NbenzResponseDTO() {
	}
	
	public NbenzResponseDTO(int code, T result) {
		this.code = code;
		this.result = result;
	}
	public NbenzResponseDTO(T result) {
		this.code = 0;
		this.result = result;
	}
	public ResponseEntity<NbenzResponseDTO<T>> buildOk() {
		return new ResponseEntity<NbenzResponseDTO<T>>(this, HttpStatus.OK);
	}
	public ResponseEntity<NbenzResponseDTO<T>> buildJH_UTF8_Ok() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json; charset=utf-8");
		return ResponseEntity.ok().headers(headers).body(this);
		
	}
	public ResponseEntity<NbenzResponseDTO<T>> buildCreated() {
		return new ResponseEntity<NbenzResponseDTO<T>>(this, HttpStatus.CREATED);
	}
	public ResponseEntity<NbenzResponseDTO<T>> buildResponse(HttpStatus status) {
		return new ResponseEntity<NbenzResponseDTO<T>>(this, status);
	}
	//generate response ok for webmethod
	public ResponseEntity buildWMResponse(HttpStatus httpStatus,String status,String message) {
		Map<String, Object>  responseWM = new HashMap<String, Object>();
		responseWM.put("status", status);
		responseWM.put("message", message);
		return new ResponseEntity<Map<String,Object>>(responseWM, httpStatus);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
	
