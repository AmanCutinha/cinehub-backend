package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.Movie;
import com.cinehub.cinehub.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Add new movie
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Update movie (partial update)
    public Movie updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id).map(existingMovie -> {
            if (updatedMovie.getTitle() != null) existingMovie.setTitle(updatedMovie.getTitle());
            if (updatedMovie.getGenre() != null) existingMovie.setGenre(updatedMovie.getGenre());
            if (updatedMovie.getRating() != null) existingMovie.setRating(updatedMovie.getRating());
            if (updatedMovie.getDescription() != null) existingMovie.setDescription(updatedMovie.getDescription());
            if (updatedMovie.getReleaseDate() != null) existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            if (updatedMovie.getDurationMinutes() != null) existingMovie.setDurationMinutes(updatedMovie.getDurationMinutes());
            if (updatedMovie.getLanguage() != null) existingMovie.setLanguage(updatedMovie.getLanguage());
            if (updatedMovie.getPosterUrl() != null) existingMovie.setPosterUrl(updatedMovie.getPosterUrl());

            return movieRepository.save(existingMovie);
        }).orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
    }

    // Delete movie
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
