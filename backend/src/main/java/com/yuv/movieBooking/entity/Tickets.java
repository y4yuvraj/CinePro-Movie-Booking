package com.yuv.movieBooking.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class Tickets {

	@Id
	private String bookingId;

	@DBRef
	private User user; // Reference to the User Entity

	@DBRef
	private Movie movie; // Reference to the Movie Entity

	@Field("theatreName")
	private String theatreName;

	private int numberOfTicket;

	private List<Integer> seatNumber;

}
