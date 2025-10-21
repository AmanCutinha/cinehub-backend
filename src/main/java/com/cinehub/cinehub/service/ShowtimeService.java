package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.Showtime;
import com.cinehub.cinehub.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
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

    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    public Showtime updateShowtime(Long id, Showtime updatedShowtime) {
        return showtimeRepository.findById(id)
                .map(s -> {
                    s.setDate(updatedShowtime.getDate());
                    s.setTime(updatedShowtime.getTime());
                    s.setPrice(updatedShowtime.getPrice());
                    s.setAvailableSeats(updatedShowtime.getAvailableSeats());
                    s.setTotalSeats(updatedShowtime.getTotalSeats());
                    return showtimeRepository.save(s);
                })
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }
}
