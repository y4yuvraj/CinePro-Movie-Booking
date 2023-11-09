package com.yuv.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuv.movieBooking.dto.BookingCountDto;
import com.yuv.movieBooking.entity.Movie;
import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.exception.MovieAlreadyExistsException;
import com.yuv.movieBooking.exception.MovieNotFoundException;
import com.yuv.movieBooking.service.AdminServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	private AdminServiceImpl adminServiceImpl;

	public void setAdminServiceImpl(AdminServiceImpl adminServiceImpl) {
		this.adminServiceImpl = adminServiceImpl;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("addMovie")
	public ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) throws MovieAlreadyExistsException {
		return new ResponseEntity<>(adminServiceImpl.saveMovie(movie), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{movieName}/delete/{movieId}")
	public ResponseEntity<String> deleteMovie(@PathVariable("movieName") String movieName,
			@PathVariable("movieId") String movieId) throws MovieNotFoundException {
		return new ResponseEntity<>(adminServiceImpl.deleteMovie(movieName, movieId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')") // working
	@GetMapping("/viewBookedTickets")
	public ResponseEntity<List<Tickets>> viewBookedTickets() {
		// Retrieve all booked tickets
		return new ResponseEntity<>(adminServiceImpl.findAllMovie(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/viewBookedTickets/{movieName}")
	public @ResponseBody ResponseEntity<BookingCountDto> viewBookedTicketsForMovie(
			@PathVariable("movieName") String movieName) throws MovieNotFoundException {
		return new ResponseEntity<>(adminServiceImpl.getBookingCountForMovie(movieName), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')") // not working
	@GetMapping("/viewBookedTicketsAndUpdateStatus/{movieName}")
	public ResponseEntity<String> viewBookedTicketsAndUpdateStatus(@PathVariable("movieName") String movieName)
			throws MovieNotFoundException {

		return new ResponseEntity<>(adminServiceImpl.getBookingStatus(movieName), HttpStatus.OK);
	}

}
