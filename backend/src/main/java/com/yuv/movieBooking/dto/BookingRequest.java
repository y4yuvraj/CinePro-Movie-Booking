package com.yuv.movieBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
	
	@NotNull(message = "Movie Name is mandatory")
	@NotBlank(message = "Movie Name is mandatory")
	private String movieName;
	@NotNull(message = "Theatre Name is mandatory")
	@NotBlank(message = "Theatre Name is mandatory")
	private String theatreName;
	@NotNull(message = "number of tickets you are booking is mandatory")
	@NotBlank(message = "number of tickets you are booking is mandatory")
	private int numberOfTickets;
}
