package com.yuv.movieBooking.service.Impl;

import java.util.List;

import com.yuv.movieBooking.dto.BookingRequest;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.exception.MovieNotFoundException;

public interface UserBookingService {

	public List<Movie> getAllMovies();
	
	public List<Movie> getMovieByName(String movieName) throws MovieNotFoundException;
	
	public String bookTicket(BookingRequest bookingRequest) throws MovieNotFoundException;
	
}
