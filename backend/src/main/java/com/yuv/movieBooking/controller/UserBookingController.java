package com.yuv.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuv.movieBooking.dto.BookingRequest;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.service.UserBookingServiceImpl;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class UserBookingController {

	@Autowired
	UserBookingServiceImpl userBookingServiceImpl;

	@GetMapping("/all")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return new ResponseEntity<>(userBookingServiceImpl.getAllMovies(), HttpStatus.OK);

	}

	@GetMapping("/movies/search/{movieName}")
	public ResponseEntity<List<Movie>> searchMovieByName(@PathVariable("movieName") String movieName)
			throws MovieNotFoundException {
		return new ResponseEntity<>(userBookingServiceImpl.getMovieByName(movieName), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/bookTickets")
	public ResponseEntity<String> bookTickets(@RequestBody BookingRequest bookingRequest)
			throws MovieNotFoundException {
		return new ResponseEntity<>(userBookingServiceImpl.bookTicket(bookingRequest), HttpStatus.OK);

	}

	public void setUserBookingServiceImpl(UserBookingServiceImpl userBookingService) {
		// TODO Auto-generated method stub
		this.userBookingServiceImpl = userBookingService;

	}

}
