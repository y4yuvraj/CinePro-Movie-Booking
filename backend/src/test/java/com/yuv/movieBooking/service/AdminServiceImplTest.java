package com.yuv.movieBooking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yuv.movieBooking.dto.BookingCountDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieAlreadyExistsException;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.repository.MovieRepository;
import com.yuv.movieBooking.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

	@Mock
	private MovieRepository movieRepository;
	@Mock
	private TicketRepository ticketRepository;

	private AdminServiceImpl adminServiceImpl;

	@BeforeEach
	void setUp() {
		this.adminServiceImpl = new AdminServiceImpl(movieRepository, ticketRepository);
	}

	@Test
	void testSaveMovie() throws MovieAlreadyExistsException {
		Movie movie = new Movie();
		movie.setMovieName("Test Movie");
		movie.setTheatreName("Test Theatre");
		when(movieRepository.findByMovieNameAndTheatreName(movie.getMovieName(), movie.getTheatreName()))
				.thenReturn(null);
		when(movieRepository.save(movie)).thenReturn(movie);
		Movie savedMovie = adminServiceImpl.saveMovie(movie);
		assertNotNull(savedMovie);
		assertEquals("Test Movie", savedMovie.getMovieName());
		assertEquals("Test Theatre", savedMovie.getTheatreName());
		verify(movieRepository, times(1)).save(movie);
	}
	
	@Test
	void testSaveMovie_MovieAlreadyExists() {
	    Movie movie = new Movie();
	    movie.setMovieName("Existing Movie");
	    movie.setTheatreName("Theater 1");
	    when(movieRepository.findByMovieNameAndTheatreName("Existing Movie", "Theater 1")).thenReturn(movie);
	    Movie newMovie = new Movie();
	    newMovie.setMovieName("Existing Movie");
	    newMovie.setTheatreName("Theater 1");
	    assertThrows(MovieAlreadyExistsException.class, () -> adminServiceImpl.saveMovie(newMovie));
	    verify(movieRepository, times(1)).findByMovieNameAndTheatreName("Existing Movie", "Theater 1");
	}

	@Test
	void testDeleteMovie() throws MovieNotFoundException {
		Movie movieToDelete = new Movie();
		movieToDelete.setMovieName("Test Movie");
		movieToDelete.setMovieId("123");
		when(movieRepository.findByMovieNameAndMovieId("Test Movie", "123")).thenReturn(movieToDelete);
		doNothing().when(movieRepository).delete(movieToDelete);
		String result = adminServiceImpl.deleteMovie("Test Movie", "123");
		assertEquals("Movie Deleted!", result);
		verify(movieRepository, times(1)).delete(movieToDelete);
	}
	
	@Test
	void testDeleteMovie_MovieNotFound() {
	    when(movieRepository.findByMovieNameAndMovieId("Nonexistent Movie", "123")).thenReturn(null);
	    assertThrows(MovieNotFoundException.class, () -> adminServiceImpl.deleteMovie("Nonexistent Movie", "123"));
	    verify(movieRepository, times(1)).findByMovieNameAndMovieId("Nonexistent Movie", "123");
	}

	@Test
	void testFindAllMovie() {
		List<Tickets> ticketsList = List.of(new Tickets(), new Tickets(), new Tickets());
		when(ticketRepository.findAll()).thenReturn(ticketsList);
		List<Tickets> result = adminServiceImpl.findAllMovie();
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	void testGetBookingCountForMovie() throws MovieNotFoundException {
		Movie movie = new Movie();
		movie.setMovieName("Test Movie");
		movie.setMovieId("123");
		movie.setAvailableTickets(10); 
		movie.setTotalTickets(100);
		when(movieRepository.findByMovieName("Test Movie")).thenReturn(movie);
		List<Tickets> bookedTickets = List.of(new Tickets(), new Tickets());
		when(ticketRepository.findByMovieMovieId("123")).thenReturn(bookedTickets);
		BookingCountDto bookingCountDto = adminServiceImpl.getBookingCountForMovie("Test Movie");
		assertEquals(2, bookingCountDto.getNumberOfUsersBookedTicket());
	}
	
	@Test
	void testGetBookingCountForMovie_MovieNotFound() {
	    when(movieRepository.findByMovieName("Nonexistent Movie")).thenReturn(null);
	    assertThrows(MovieNotFoundException.class, () -> adminServiceImpl.getBookingCountForMovie("Nonexistent Movie"));
	    verify(movieRepository, times(1)).findByMovieName("Nonexistent Movie");
	}
	
	@Test
	void testGetBookingStatus() throws MovieNotFoundException {
	    Movie movie = new Movie();
	    movie.setMovieName("Test Movie");
	    movie.setMovieId("123");
	    movie.setAvailableTickets(10); 
	    when(movieRepository.findByMovieName("Test Movie")).thenReturn(movie);
	    List<Tickets> bookedTickets = List.of(new Tickets(), new Tickets());
	    when(ticketRepository.findByMovieMovieId("123")).thenReturn(bookedTickets);
	    String result = adminServiceImpl.getBookingStatus("Test Movie");
	    assertEquals("Total user who booked tickets for Test Movie: 2. Available Tickets: 10. Movie status updated to: BOOK_ASAP", result);
	    assertEquals("BOOK_ASAP", movie.getStatus());
	    verify(movieRepository, times(1)).save(movie);
	}
	
	@Test
	void testGetBookingStatus_MovieNotFound() {
	    when(movieRepository.findByMovieName("Nonexistent Movie")).thenReturn(null);
	    assertThrows(MovieNotFoundException.class, () -> adminServiceImpl.getBookingStatus("Nonexistent Movie"));
	    verify(movieRepository, times(1)).findByMovieName("Nonexistent Movie");
	}
	
	@Test
	void testGetBookingStatus_UpdateStatus() throws MovieNotFoundException {
	    Movie movie = new Movie();
	    movie.setMovieName("Test Movie");
	    movie.setMovieId("123");
	    movie.setAvailableTickets(10);
	    when(movieRepository.findByMovieName("Test Movie")).thenReturn(movie);
	    List<Tickets> bookedTickets = List.of(new Tickets(), new Tickets());
	    when(ticketRepository.findByMovieMovieId("123")).thenReturn(bookedTickets);
	    String result = adminServiceImpl.getBookingStatus("Test Movie");
	    assertEquals("Total user who booked tickets for Test Movie: 2. Available Tickets: 10. Movie status updated to: BOOK_ASAP", result);
	    assertEquals("BOOK_ASAP", movie.getStatus());
	    verify(movieRepository, times(1)).save(movie);
	}
	
	@Test
	void testGetBookingStatus_UpdateStatusToSoldOut() throws MovieNotFoundException {
	    Movie movie = new Movie();
	    movie.setMovieName("Test Movie");
	    movie.setMovieId("123");
	    movie.setAvailableTickets(2); // Setting a low number of available tickets
	    when(movieRepository.findByMovieName("Test Movie")).thenReturn(movie);
	    List<Tickets> bookedTickets = List.of(new Tickets(), new Tickets(), new Tickets());
	    when(ticketRepository.findByMovieMovieId("123")).thenReturn(bookedTickets);
	    String result = adminServiceImpl.getBookingStatus("Test Movie");
	    assertEquals("Total user who booked tickets for Test Movie: 3. Available Tickets: 2. Movie status updated to: SOLD_OUT", result);
	    assertEquals("SOLD_OUT", movie.getStatus());
	    verify(movieRepository, times(1)).save(movie);
	}

}
