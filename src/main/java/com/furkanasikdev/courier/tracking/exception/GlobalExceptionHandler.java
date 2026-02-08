package com.furkanasikdev.courier.tracking.exception;

import com.furkanasikdev.courier.tracking.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		log.warn("Validation failed: {}", errors);

		return ResponseUtil.problemDetail(
				HttpStatus.BAD_REQUEST,
				"Validation Failed",
				"One or more fields have invalid values",
				errors
		);
	}

	@ExceptionHandler(CourierNotFoundException.class)
	public ProblemDetail handleCourierNotFound(CourierNotFoundException ex) {
		log.warn("Courier not found: {}", ex.getMessage());

		return ResponseUtil.problemDetail(
				HttpStatus.NOT_FOUND,
				"Courier Not Found",
				ex.getMessage()
		);
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGeneral(Exception ex) {
		log.error("Unexpected error: {}", ex.getMessage(), ex);

		return ResponseUtil.problemDetail(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"Internal Server Error",
				"An unexpected error occurred"
		);
	}
}