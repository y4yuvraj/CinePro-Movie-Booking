package com.yuv.movieBooking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

//import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuv.movieBooking.dto.BookingRequest;
import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.repository.MovieRepository;
import com.yuv.movieBooking.repository.TicketRepository;
import com.yuv.movieBooking.repository.UserRepository;

class UserBookingServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private MovieRepository movieRepository;
	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private UserBookingServiceImpl userBookingServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllMovies() {
		Movie movie1 = new Movie("123", "Movie 1", "Theatre 1", "BOOK_ASAP", 10, 20, 20);
		Movie movie2 = new Movie("124", "Movie 2", "Theatre 2", "BOOK_ASAP", 5, 30, 30);
		when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2));
		List<Movie> movies = userBookingServiceImpl.getAllMovies();
		assertEquals(2, movies.size());
		assertEquals(movie1, movies.get(0));
		assertEquals(movie2, movies.get(1));
	}

	@Test
	void testGetMovieByName() throws MovieNotFoundException {
		Movie movie1 = new Movie("123", "Movie 1", "Theatre 1", "BOOK_ASAP", 10, 20, 20);
		Movie movie2 = new Movie("124", "Movie 2", "Theatre 2", "BOOK_ASAP", 5, 30, 30);
		List<Movie> sampleMovies = List.of(movie1, movie2);
		when(movieRepository.findByMovieNameContainingIgnoreCase("Movie")).thenReturn(sampleMovies);
		when(movieRepository.findByMovieNameContainingIgnoreCase("Non-Existent Movie")).thenReturn(null);
		List<Movie> movies = userBookingServiceImpl.getMovieByName("Movie");
		assertEquals(2, movies.size());

	}

	@Test
	void testGetMovieByNameMovieNotFound() {
	    when(movieRepository.findByMovieNameContainingIgnoreCase("Non-Existent Movie")).thenReturn(Collections.emptyList());
	    try {
	        userBookingServiceImpl.getMovieByName("Non-Existent Movie");
	        fail("Expected MovieNotFoundException");
	    } catch (MovieNotFoundException e) {
	        assertEquals("Non-Existent Movie does not exists.", e.getMessage());
	    }
	}

	@Test
	void testBookTicket() throws MovieNotFoundException {
		Movie movie = new Movie("123", "Test Movie", "Test Theatre", "BOOK_ASAP", 10, 20, 20);
		when(movieRepository.findByMovieNameAndTheatreName("Test Movie", "Test Theatre")).thenReturn(movie);
		when(ticketRepository.save(any(Tickets.class))).thenReturn(new Tickets());
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("testuser");
		UserDto userDto = new UserDto();
		userDto.setLoginId("testuser");
		when(userRepository.findByLoginId("testuser")).thenReturn(userDto);
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setMovieName("Test Movie");
		bookingRequest.setTheatreName("Test Theatre");
		bookingRequest.setNumberOfTickets(2); // Requesting 2 tickets
		String result = userBookingServiceImpl.bookTicket(bookingRequest);
		assertEquals("Tickets booked successfully!", result);
		assertEquals(18, movie.getAvailableTickets());
		assertEquals("BOOK_ASAP", movie.getStatus());
	}


	@Test
	void testBookTicketNotEnoughAvailableTickets() throws MovieNotFoundException {
		Movie movie = new Movie("123", "Test Movie", "Test Theatre", "BOOK_ASAP", 10, 20, 20);
		when(movieRepository.findByMovieNameAndTheatreName("Test Movie", "Test Theatre")).thenReturn(movie);
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("testuser");
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setMovieName("Test Movie");
		bookingRequest.setTheatreName("Test Theatre");
		bookingRequest.setNumberOfTickets(21); 
		String result = userBookingServiceImpl.bookTicket(bookingRequest);
		assertEquals("Not enough available tickets. Available: 20", result);
	}

	@Test
	    void testBookTicketMovieNotFound() {
	        when(movieRepository.findByMovieNameAndTheatreName("Test Movie", "Test Theatre")).thenReturn(null);
	        BookingRequest bookingRequest = new BookingRequest();
	        bookingRequest.setMovieName("Test Movie");
	        bookingRequest.setTheatreName("Test Theatre");
	        bookingRequest.setNumberOfTickets(2);
	        assertThrows(MovieNotFoundException.class, () -> userBookingServiceImpl.bookTicket(bookingRequest));
	    }

	@Test
	void testBookTicketSoldOut() throws MovieNotFoundException {
		Movie movie = new Movie("123", "Test Movie", "Test Theatre", "BOOK_ASAP", 10, 5, 5);
		when(movieRepository.findByMovieNameAndTheatreName("Test Movie", "Test Theatre")).thenReturn(movie);
		when(ticketRepository.save(any(Tickets.class))).thenReturn(new Tickets());
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("testuser");
		UserDto userDto = new UserDto();
		userDto.setLoginId("testuser");
		when(userRepository.findByLoginId("testuser")).thenReturn(userDto);
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setMovieName("Test Movie");
		bookingRequest.setTheatreName("Test Theatre");
		bookingRequest.setNumberOfTickets(5); // Requesting all available tickets
		String result = userBookingServiceImpl.bookTicket(bookingRequest);
		assertEquals("Tickets booked successfully!", result);
		assertEquals(0, movie.getAvailableTickets());
		assertEquals("SOLD_OUT", movie.getStatus());
	}

}
