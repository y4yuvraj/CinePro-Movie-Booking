package com.yuv.movieBooking.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.entity.User;

@DataMongoTest
@TestInstance(Lifecycle.PER_CLASS)
class TicketRepositoryTest {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MovieRepository movieRepository;

	private Movie movie;
	private Tickets ticket;
	private User user;
	private List<Integer> seat;

	@BeforeAll
	void setUp() {
		seat = new ArrayList<>();
		seat.add(1);
		seat.add(2);
		user = new User("123", "yuv", "singh", "abc@example.com", "1234", "1234567890", "ROLE_USER");
		movie = new Movie("123", "tiger", "srs", "BOOK_ASAP", 10, 100, 100);
		ticket = new Tickets("1", user, movie, "srs", 2, seat);
		userRepository.save(user);
		movieRepository.save(movie);
		ticketRepository.save(ticket);
	}

	@Test
	void testFindByMovieMovieId() {
		List<Tickets> tickets = ticketRepository.findByMovieMovieId("123");
		assertNotNull(tickets);
		assertEquals(1, tickets.size());

	}

	@AfterAll
	void tearDown() {
		ticketRepository.delete(ticket);
		userRepository.delete(user);
		movieRepository.delete(movie);
	}

}
