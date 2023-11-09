package com.yuv.movieBooking.service.Impl;

import java.util.List;

import com.yuv.movieBooking.dto.BookingCountDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieAlreadyExistsException;
import com.yuv.movieBooking.exception.MovieNotFoundException;

public interface AdminService {

	public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException; 
	
	public String deleteMovie(String movieName,String movieId) throws MovieNotFoundException;
	
	public List<Tickets> findAllMovie();
	
	public BookingCountDto getBookingCountForMovie(String movieName) throws MovieNotFoundException;
	
	public String getBookingStatus(String movieName) throws MovieNotFoundException;
	
}
