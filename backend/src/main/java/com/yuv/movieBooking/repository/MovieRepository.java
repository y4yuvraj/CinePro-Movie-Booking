package com.yuv.movieBooking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuv.movieBooking.entity.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

	Movie findByMovieNameAndTheatreName(String movieName, String theatreName);

	Movie findByMovieNameAndMovieId(String movieName, String movieId);

	Movie findByMovieName(String movieName);

	List<Movie> findByMovieNameContainingIgnoreCase(String movieName);
}
