package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.Movie;
import com.cinehub.cinehub.model.Showtime;
import com.cinehub.cinehub.repository.MovieRepository;
import com.cinehub.cinehub.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public List<Showtime> getShowtimesByMovie(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }

    public Showtime addShowtime(Showtime showtime, Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        showtime.setMovie(movie);
        if (showtime.getAvailableSeats() == null) {
            showtime.setAvailableSeats(showtime.getTotalSeats());
        }
        return showtimeRepository.save(showtime);
    }

    public Showtime updateShowtime(Long id, Showtime updatedShowtime, Long movieId) {
        return showtimeRepository.findById(id)
                .map(existing -> {
                    if (movieId != null) {
                        Movie movie = movieRepository.findById(movieId)
                                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));
                        existing.setMovie(movie);
                    }
                    existing.setDate(updatedShowtime.getDate());
                    existing.setTime(updatedShowtime.getTime());
                    existing.setPrice(updatedShowtime.getPrice());
                    existing.setTotalSeats(updatedShowtime.getTotalSeats());
                    existing.setAvailableSeats(updatedShowtime.getAvailableSeats());
                    return showtimeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }
}