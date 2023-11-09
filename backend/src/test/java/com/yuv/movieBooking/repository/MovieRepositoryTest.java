package com.yuv.movieBooking.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.yuv.movieBooking.entity.Movie;

@DataMongoTest
@TestInstance(Lifecycle.PER_CLASS)
class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;

	private Movie movie1;
	private Movie movie2;

	@BeforeAll
	void setUp() {
		movie1 = new Movie("123", "tiger", "srs", "BOOK_ASAP", 10, 100, 100);
		movieRepository.save(movie1);
		movie2 = new Movie("456", "Tiger King", "srs2", "BOOK_ASAP", 5, 50, 50);
		movieRepository.save(movie2);
	}

	@Test
	void testFindByMovieNameAndTheatreName() {
		Movie foundMovie = movieRepository.findByMovieNameAndTheatreName("tiger", "srs");
		assertNotNull(foundMovie);
		assertEquals("tiger", foundMovie.getMovieName());
		assertEquals("srs", foundMovie.getTheatreName());
	}

	@Test
	void testFindByMovieNameAndMovieId() {
		Movie foundMovie = movieRepository.findByMovieNameAndMovieId("tiger", "123");
		assertNotNull(foundMovie);
		assertEquals("tiger", foundMovie.getMovieName());
		assertEquals("123", foundMovie.getMovieId());
	}

	@Test
	void testFindByMovieName() {
		Movie foundMovie = movieRepository.findByMovieName("tiger");
		assertNotNull(foundMovie);
		assertEquals("tiger", foundMovie.getMovieName());
	}

	@Test
	void testFindByMovieNameContainingIgnoreCase() {
		List<Movie> foundMovies = movieRepository.findByMovieNameContainingIgnoreCase("tiger");
		assertEquals(2, foundMovies.size()); // Both "tiger" and "Tiger King" should be found
	}

	@AfterAll
	void tearDown() {
		movieRepository.delete(movie1);
		movieRepository.delete(movie2);

	}

}
