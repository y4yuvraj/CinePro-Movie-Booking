package com.yuv.movieBooking.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public
class ErrorResponseHandling {
	private String error;
	private String message;

	public ErrorResponseHandling(String error, String message) {
		this.error = error;
		this.message = message;
	}

}
