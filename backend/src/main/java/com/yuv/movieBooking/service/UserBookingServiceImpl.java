package com.yuv.movieBooking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuv.movieBooking.dto.BookingRequest;
import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.repository.MovieRepository;
import com.yuv.movieBooking.repository.TicketRepository;
import com.yuv.movieBooking.repository.UserRepository;
import com.yuv.movieBooking.service.Impl.UserBookingService;

@Service
public class UserBookingServiceImpl implements UserBookingService {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private TicketRepository ticketsRepository;
	@Autowired
	private UserRepository userRepository;



	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public List<Movie> getMovieByName(String movieName) throws MovieNotFoundException {
		List<Movie> movies = movieRepository.findByMovieNameContainingIgnoreCase(movieName);

		if (movies != null && !movies.isEmpty()) {
			return movies;
		} else {
			throw new MovieNotFoundException(movieName + " does not exists.");
		}
	}

	@Override
	public String bookTicket(BookingRequest bookingRequest) throws MovieNotFoundException {
		Movie movie = movieRepository.findByMovieNameAndTheatreName(bookingRequest.getMovieName(),
				bookingRequest.getTheatreName());

		if (movie != null) {
			int numberOfTicketsRequested = bookingRequest.getNumberOfTickets();
			int availableTickets = movie.getAvailableTickets();
			int totalTicket = movie.getTotalTickets();

			if (numberOfTicketsRequested <= availableTickets) {
				int seat = totalTicket - availableTickets;
				List<Integer> seatNumbers = new ArrayList<>();
				for (int i = seat + 1; i <= seat + numberOfTicketsRequested; i++) {
					seatNumbers.add(i);
				}

				availableTickets -= numberOfTicketsRequested;
				movie.setAvailableTickets(availableTickets);

				if (availableTickets == 0) {
					movie.setStatus("SOLD_OUT");
				}

				movieRepository.save(movie);

				Tickets booking = new Tickets();
				booking.setUser(getCurrentUser()); // method to get the current loggedIn user
				booking.setMovie(movie);
				booking.setTheatreName(movie.getTheatreName());
				booking.setNumberOfTicket(numberOfTicketsRequested);
				booking.setSeatNumber(seatNumbers);

				ticketsRepository.save(booking);

				return "Tickets booked successfully!";
			} else {
				return "Not enough available tickets. Available: " + availableTickets;
			}

		} else {
			throw new MovieNotFoundException(bookingRequest.getMovieName() + " Movie does not exist for "+bookingRequest.getTheatreName());
		}
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String loginId = authentication.getName();
			UserDto userDto = userRepository.findByLoginId(loginId);
			User user = new  User(userDto.getLoginId(), userDto.getFirstName(), userDto.getLastName(),
					userDto.getEmail(), userDto.getPassword(), userDto.getContactNumber(), userDto.getRole());
			return user;
		} else {
			// Handle the case where no user is authenticated (e.g., anonymous access)
			throw new UsernameNotFoundException("try again");
		}
	}

	public UserBookingServiceImpl(UserRepository userRepository, MovieRepository movieRepository,
			TicketRepository ticketRepository) {
		// TODO Auto-generated constructor stub
		this.userRepository=userRepository;
		this.movieRepository=movieRepository;
		this.ticketsRepository=ticketRepository;
	}
}

