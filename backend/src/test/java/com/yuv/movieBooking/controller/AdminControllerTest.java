package com.yuv.movieBooking.controller;

import com.yuv.movieBooking.dto.BookingCountDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieAlreadyExistsException;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.service.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminControllerTest {

	@Mock
	private AdminServiceImpl adminService;

	private AdminController adminController;

	@BeforeEach
	void setUp() {
		adminController = new AdminController();
		adminService = Mockito.mock(AdminServiceImpl.class); // Mock the service
		adminController.setAdminServiceImpl(adminService); // Inject the mock service
	}

	@Test
	void testAddMovie() throws MovieAlreadyExistsException {
		Movie movie = new Movie();
		Mockito.when(adminService.saveMovie(movie)).thenReturn(movie);

		ResponseEntity<Movie> response = adminController.addMovie(movie);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(movie, response.getBody());
	}

	@Test
	void testDeleteMovie() throws MovieNotFoundException {
		String movieName = "MovieName";
		String movieId = "MovieId";
		Mockito.when(adminService.deleteMovie(movieName, movieId)).thenReturn("Movie Deleted");

		ResponseEntity<String> response = adminController.deleteMovie(movieName, movieId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Movie Deleted", response.getBody());
	}

	@Test
	void testViewBookedTickets() {
		List<Tickets> ticketsList = List.of(new Tickets(), new Tickets());
		Mockito.when(adminService.findAllMovie()).thenReturn(ticketsList);

		ResponseEntity<List<Tickets>> response = adminController.viewBookedTickets();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ticketsList, response.getBody());
	}

	@Test
	void testViewBookedTicketsForMovie() throws MovieNotFoundException {
		String movieName = "MovieName";
		BookingCountDto bookingCountDto = new BookingCountDto();
		Mockito.when(adminService.getBookingCountForMovie(movieName)).thenReturn(bookingCountDto);

		ResponseEntity<BookingCountDto> response = adminController.viewBookedTicketsForMovie(movieName);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(bookingCountDto, response.getBody());
	}

	@Test
	void testViewBookedTicketsAndUpdateStatus() throws MovieNotFoundException {
		String movieName = "MovieName";
		Mockito.when(adminService.getBookingStatus(movieName)).thenReturn("Status");

		ResponseEntity<String> response = adminController.viewBookedTicketsAndUpdateStatus(movieName);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Status", response.getBody());
	}
}
