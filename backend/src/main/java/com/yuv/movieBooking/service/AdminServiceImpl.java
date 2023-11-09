package com.yuv.movieBooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.yuv.movieBooking.dto.BookingCountDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieAlreadyExistsException;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.repository.MovieRepository;
import com.yuv.movieBooking.repository.TicketRepository;
import com.yuv.movieBooking.service.Impl.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TicketRepository ticketsRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException {
		if (movieRepository.findByMovieNameAndTheatreName(movie.getMovieName(), movie.getTheatreName()) != null) {
			throw new MovieAlreadyExistsException(
					"Theater: " + movie.getTheatreName() + " already has this movie: " + movie.getMovieName());
		}
		return movieRepository.save(movie);
	}

	@Override
	public String deleteMovie(String movieName, String movieId) throws MovieNotFoundException {
		Movie movieToDelete = movieRepository.findByMovieNameAndMovieId(movieName, movieId);
		if (movieToDelete != null) {
			movieRepository.delete(movieToDelete);
		} else {
			throw new MovieNotFoundException(movieName + " does not exist");
		}
		return "Movie Deleted!";
	}

	@Override
	public List<Tickets> findAllMovie() {
		return ticketsRepository.findAll();
	}

	@Override
	public BookingCountDto getBookingCountForMovie(String movieName) throws MovieNotFoundException {
		BookingCountDto bookingCountDto = new BookingCountDto();
		Movie movie = movieRepository.findByMovieName(movieName);
		if (movie != null) {
			String movieId = movie.getMovieId();
			List<Tickets> bookedTickets = ticketsRepository.findByMovieMovieId(movieId);
			bookingCountDto.setNumberOfUsersBookedTicket(bookedTickets.size());
			bookingCountDto.setTotalTicketBooked(movie.getTotalTickets() - movie.getAvailableTickets());
			return bookingCountDto;
		} else {
			throw new MovieNotFoundException(movieName + " does not exist");
		}
	}

	@Override
	public String getBookingStatus(String movieName) throws MovieNotFoundException {
		Movie movie = movieRepository.findByMovieName(movieName);
		if (movie == null) {
			throw new MovieNotFoundException(movieName + " does not exist");
		}
		String movieId = movie.getMovieId();
		List<Tickets> bookedTickets = ticketsRepository.findByMovieMovieId(movieId);
		int bookedTicketCount = bookedTickets.size();

		if (bookedTicketCount >= movie.getAvailableTickets()) {
			movie.setStatus("SOLD_OUT");
		} else {
			movie.setStatus("BOOK_ASAP");
		}
		movieRepository.save(movie);
		this.kafkaTemplate.send("movie",
				"Total user who booked tickets for " + movieName + ": " + bookedTicketCount + ". Available Tickets: "
						+ movie.getAvailableTickets() + ". Movie status updated to: " + movie.getStatus());

		return "Total user who booked tickets for " + movieName + ": " + bookedTicketCount + ". Available Tickets: "
				+ movie.getAvailableTickets() + ". Movie status updated to: " + movie.getStatus();
	}

	public AdminServiceImpl(MovieRepository movieRepository, TicketRepository ticketRepository) {
		// TODO Auto-generated constructor stub
		this.movieRepository = movieRepository;
		this.ticketsRepository = ticketRepository;
	}

}
