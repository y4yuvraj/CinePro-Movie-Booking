package com.yuv.movieBooking.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuv.movieBooking.dto.ErrorResponseHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        
	        ErrorResponseHandling errorResponse = new ErrorResponseHandling("Unauthorized", "Access denied. You must be authenticated.");

	        ObjectMapper mapper = new ObjectMapper();
	        String jsonResponse = mapper.writeValueAsString(errorResponse);

	        response.setContentType("application/json");
	        response.getWriter().write(jsonResponse);

	} 

}

