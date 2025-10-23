package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.Movie;
import com.cinehub.cinehub.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        if (updatedMovie.getTitle() != null) existing.setTitle(updatedMovie.getTitle());
        if (updatedMovie.getGenre() != null) existing.setGenre(updatedMovie.getGenre());
        if (updatedMovie.getDescription() != null) existing.setDescription(updatedMovie.getDescription());
        if (updatedMovie.getLanguage() != null) existing.setLanguage(updatedMovie.getLanguage());
        if (updatedMovie.getReleaseDate() != null) existing.setReleaseDate(updatedMovie.getReleaseDate());
        if (updatedMovie.getPosterUrl() != null) existing.setPosterUrl(updatedMovie.getPosterUrl());
        if (updatedMovie.getDurationMinutes() != 0) existing.setDurationMinutes(updatedMovie.getDurationMinutes());
        if (updatedMovie.getRating() != 0) existing.setRating(updatedMovie.getRating());

        return movieRepository.save(existing);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
