package com.nbenz.sbootstarter.controller;


import com.nbenz.sbootstarter.util.RestEndPoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import com.nbenz.sbootstarter.dto.NbenzResponseDTO;

public interface HelloController{

    @GetMapping(RestEndPoints.HELLO_TEST)
	public ResponseEntity<NbenzResponseDTO<String>> index();
}
