package com.nbenz.sbootstarter.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;
import com.nbenz.sbootstarter.controller.HelloController;
import org.springframework.http.ResponseEntity;

import  com.nbenz.sbootstarter.exception.*;
import com.nbenz.sbootstarter.dto.NbenzResponseDTO;


@RestController
public class HelloControllerImpl implements HelloController{

    private static final Logger logger = LoggerFactory.getLogger(HelloControllerImpl.class);

	@Override
	public ResponseEntity<NbenzResponseDTO<String>> index() {
		logger.info("calling Index root....");
		// throw new NbenzException(new NbenzError(400, "Error client Baaad developper :/ "));
		return new NbenzResponseDTO<>("Greetings from Nouri Spring Boot App!").buildOk();
	}
} 
