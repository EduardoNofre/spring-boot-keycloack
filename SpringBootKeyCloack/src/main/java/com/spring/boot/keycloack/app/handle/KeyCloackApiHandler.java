package com.spring.boot.keycloack.app.handle;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Hidden
@ControllerAdvice
public class KeyCloackApiHandler extends ResponseEntityExceptionHandler implements Serializable{

	private static final long serialVersionUID = 4790038517549729374L;

	public KeyCloackApiHandler() {
		super();
	}
	
	/**
	 * Tratamento de erro chamando API - rest
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handle(HttpClientErrorException e){
		try {
			return new ResponseEntity<>(e.getResponseBodyAsString(),getHeaderJson(), e.getStatusCode());
		} catch (Exception ex) { 
			return new ResponseEntity<>(ex.getMessage(),e.getStatusCode()); 
		}
	}
	
	/**
	 * Tratamento de erro
	 * @param e
	 * @return
	 */
	@ExceptionHandler(KeyCloackException.class)
	public ResponseEntity<ErrorResponse> handle(KeyCloackException e){
		if (e.getStatus().equals(HttpStatus.NO_CONTENT)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} 
		return montaMsg(e.getMessage(), e.getStatus()); 
	}

	/**
	 * Serviço indisponivel
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(code=HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException e) {
		log.error(String.format("[%s] - %s",HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()));
		return montaMsg("[SERVIÇO INDISPONÍVEL]",HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	/**
	 * Erro geral
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		log.error(e.getMessage(),e);
		return montaMsg(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Erro acesso
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ErrorResponse> handle(AccessDeniedException e){
		return montaMsg(e.getMessage(), HttpStatus.UNAUTHORIZED); 
	}
	
	private ResponseEntity<ErrorResponse> montaMsg(String msg,HttpStatus status){
		return new ResponseEntity<>(new ErrorResponse(msg, status),	KeyCloackApiHandler.getHeaderJson(), status);
	}
	
    public static HttpHeaders getHeaderJson() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	return headers;
    }
    
    /* 
	 * Usado pelo Bean Validator
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        String msg = ex.getMessage();
        HttpStatus statusRetorno = status;
        if (!errorList.isEmpty()) {
        	msg = errorList.toString().replace("[", "").replace("]", "");
        	statusRetorno = HttpStatus.CONFLICT;
        }
        return handleExceptionInternal(ex, new ErrorResponse(msg, statusRetorno), headers, statusRetorno, request);
    }
}
