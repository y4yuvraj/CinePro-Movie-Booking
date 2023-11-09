package com.yuv.movieBooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {

	@Id
	private String movieId;

	@Indexed(unique = true)
	@NotNull(message = "Movie Name is mandatory")
	@NotBlank(message = "Movie Name is mandatory")
	@Field("movieName")
	private String movieName;

	@Indexed(unique = true)
	@NotNull(message = "Theatre Name is mandatory")
	@NotBlank(message = "Theatre Name is mandatory")
	@Field("theatreName")
	private String theatreName;

	private String status = "BOOK_ASAP"; // can be SOLD_OUT BOOK_ASAP

	@NotNull(message = "price is mandatory")
	private int price;

	@NotNull(message = "total tickets are mandatory")
	private int totalTickets;

	private int availableTickets;

	//all argument constructor
	public Movie(String movieId,
			@NotNull(message = "Movie Name is mandatory") @NotBlank(message = "Movie Name is mandatory") String movieName,
			@NotNull(message = "Theatre Name is mandatory") @NotBlank(message = "Theatre Name is mandatory") String theatreName,
			String status, @NotNull(message = "price is mandatory") int price,
			@NotNull(message = "total tickets are mandatory") int totalTickets, int availableTickets) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.status = status;
		this.price = price;
		this.totalTickets = totalTickets;
		this.availableTickets = availableTickets;
	}
	
	

}
