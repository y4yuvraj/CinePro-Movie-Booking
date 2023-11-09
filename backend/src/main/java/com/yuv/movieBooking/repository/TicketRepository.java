package com.yuv.movieBooking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuv.movieBooking.entity.Tickets;
import com.yuv.movieBooking.entity.User;

@Repository
public interface TicketRepository extends MongoRepository<Tickets, String> {
	List<Tickets> findByMovieMovieId(String movieId);
}