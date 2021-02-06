package com.nbenz.sbootstarter.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import com.nbenz.sbootstarter.dto.NbenzResponseDTO;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value =
            {IllegalArgumentException.class, IllegalStateException.class, ResourceConflictException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorResponse(
                        HttpStatus.CONFLICT.value(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, BadRequestException.class})
    protected ResponseEntity<Object> handleBad(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return handleBad(ex, request);
    }

    @ExceptionHandler(value = {NbenzException.class})
    protected ResponseEntity<NbenzResponseDTO<String>> handleNbenzError(NbenzException ex, WebRequest request) {

        log.error(ex.getMessage());
        if(ex.getError().getCode() == 401)
        	return new NbenzResponseDTO<String>(ex.getError().getCode(), ex.getError().getDescription()).buildResponse(HttpStatus.UNAUTHORIZED);
        return new NbenzResponseDTO<String>(ex.getError().getCode(), ex.getError().getDescription()).buildOk();
    }
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<NbenzResponseDTO<String>> handleNotFoundError(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        return new NbenzResponseDTO<String>(404, ex.getMessage()).buildResponse(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> handleErrorAcessDenied(Exception ex, WebRequest request) {

        log.error("Access Denied for {}",((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return handleExceptionInternal(ex, new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(), "Access Denied"),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<NbenzResponseDTO<String>> handleError(Exception ex, WebRequest request) {

        log.error(ex.getMessage(), ex);
        return new NbenzResponseDTO<String>(500, "Internal Error").buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);       
    }
    
    @ExceptionHandler({InvalidFormatException.class, MismatchedInputException.class})
    public ResponseEntity<NbenzResponseDTO<String>> handlerIllegalArgumentException(JsonProcessingException exception,
    		WebRequest request) throws IOException {
        	log.error(exception.getMessage(), exception);
        	 return new NbenzResponseDTO<String>(500, "Invalid Json").buildOk();
       
    }
}
