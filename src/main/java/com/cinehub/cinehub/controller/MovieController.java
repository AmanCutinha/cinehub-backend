package com.cinehub.cinehub.controller;

import com.cinehub.cinehub.model.Movie;
import com.cinehub.cinehub.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:5173"}, allowCredentials = "true")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ✅ Get all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // ✅ Get a single movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Movie not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // ✅ Add a new movie
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        try {
            Movie createdMovie = movieService.addMovie(movie);
            logger.info("Added new movie: {}", createdMovie.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
        } catch (Exception e) {
            logger.error("Error adding movie: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Update existing movie
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        return movieService.getMovieById(id)
                .map(existingMovie -> {
                    if (updatedMovie.getTitle() != null) existingMovie.setTitle(updatedMovie.getTitle());
                    if (updatedMovie.getGenre() != null) existingMovie.setGenre(updatedMovie.getGenre());
                    if (updatedMovie.getRating() != null) existingMovie.setRating(updatedMovie.getRating());
                    if (updatedMovie.getDescription() != null) existingMovie.setDescription(updatedMovie.getDescription());
                    if (updatedMovie.getReleaseDate() != null) existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
                    if (updatedMovie.getDurationMinutes() != null) existingMovie.setDurationMinutes(updatedMovie.getDurationMinutes());
                    if (updatedMovie.getLanguage() != null) existingMovie.setLanguage(updatedMovie.getLanguage());
                    if (updatedMovie.getPosterUrl() != null) existingMovie.setPosterUrl(updatedMovie.getPosterUrl());

                    Movie saved = movieService.addMovie(existingMovie);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // ✅ Delete movie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieService.deleteMovie(id);
            logger.info("Deleted movie with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("Failed to delete movie with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
