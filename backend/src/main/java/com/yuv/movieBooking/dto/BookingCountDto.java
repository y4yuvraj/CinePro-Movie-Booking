package com.yuv.movieBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCountDto {
	
	private int numberOfUsersBookedTicket;
	private int totalTicketBooked;
	
}
