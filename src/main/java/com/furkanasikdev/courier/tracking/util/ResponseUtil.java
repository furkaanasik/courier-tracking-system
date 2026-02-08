package com.furkanasikdev.courier.tracking.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.util.Map;

public final class ResponseUtil {

	private ResponseUtil() {
		//
	}

	public static ProblemDetail problemDetail(HttpStatus status, String title, String detail) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle(title);
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;
	}

	public static ProblemDetail problemDetail(HttpStatus status, String title, String detail, Map<String, String> errors) {
		ProblemDetail problemDetail = problemDetail(status, title, detail);
		problemDetail.setProperty("errors", errors);
		return problemDetail;
	}
}