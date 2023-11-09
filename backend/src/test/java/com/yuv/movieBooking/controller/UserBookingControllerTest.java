package com.yuv.movieBooking.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yuv.movieBooking.dto.BookingRequest;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.service.UserBookingServiceImpl;

class UserBookingControllerTest {

	private UserBookingController userBookingController;

	@Mock
	private UserBookingServiceImpl userBookingService;

	@BeforeEach
	void setUp() {
		userBookingService = mock(UserBookingServiceImpl.class);
		userBookingController = new UserBookingController();
		userBookingController.setUserBookingServiceImpl(userBookingService);
	}

	@Test
	void testGetAllMovies() {
		List<Movie> mockMovies = new ArrayList<>();

		Mockito.when(userBookingService.getAllMovies()).thenReturn(mockMovies);

		ResponseEntity<List<Movie>> response = userBookingController.getAllMovies();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockMovies, response.getBody());
	}

	@Test
	void testSearchMovieByName() throws MovieNotFoundException {
		String movieName = "Test Movie";
		List<Movie> mockMovies = new ArrayList<>();

		Mockito.when(userBookingService.getMovieByName(movieName)).thenReturn(mockMovies);

		ResponseEntity<List<Movie>> response = userBookingController.searchMovieByName(movieName);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockMovies, response.getBody());
	}

	@Test
	void testBookTickets() throws MovieNotFoundException {
		BookingRequest bookingRequest = new BookingRequest();

		String expectedResult = "Booking successful"; // Define the expected result

		Mockito.when(userBookingService.bookTicket(bookingRequest)).thenReturn(expectedResult);

		ResponseEntity<String> response = userBookingController.bookTickets(bookingRequest);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResult, response.getBody());
	}

}
