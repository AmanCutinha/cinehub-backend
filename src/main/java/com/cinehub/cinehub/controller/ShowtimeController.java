package com.cinehub.cinehub.controller;

import com.cinehub.cinehub.model.Showtime;
import com.cinehub.cinehub.service.ShowtimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/showtimes")
@CrossOrigin(origins = "http://localhost:8080")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping
    public List<Showtime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    @GetMapping("/movie/{movieId}")
    public List<Showtime> getShowtimesByMovie(@PathVariable Long movieId) {
        return showtimeService.getShowtimesByMovie(movieId);
    }

    @PostMapping
    public ResponseEntity<?> addShowtime(@RequestBody Map<String, Object> payload) {
        try {
            Long movieId = ((Number) payload.get("movieId")).longValue();

            Showtime showtime = new Showtime();
            showtime.setDate(LocalDate.parse((String) payload.get("date")));
            showtime.setTime(LocalTime.parse((String) payload.get("time")));
            showtime.setTotalSeats(((Number) payload.get("totalSeats")).intValue());
            showtime.setAvailableSeats(((Number) payload.getOrDefault("availableSeats", payload.get("totalSeats"))).intValue());
            showtime.setPrice(((Number) payload.get("price")).doubleValue());

            return ResponseEntity.ok(showtimeService.addShowtime(showtime, movieId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating showtime: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShowtime(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Long movieId = payload.get("movieId") != null ? ((Number) payload.get("movieId")).longValue() : null;

            Showtime showtime = new Showtime();
            if (payload.get("date") != null)
                showtime.setDate(LocalDate.parse((String) payload.get("date")));
            if (payload.get("time") != null)
                showtime.setTime(LocalTime.parse((String) payload.get("time")));
            if (payload.get("totalSeats") != null)
                showtime.setTotalSeats(((Number) payload.get("totalSeats")).intValue());
            if (payload.get("availableSeats") != null)
                showtime.setAvailableSeats(((Number) payload.get("availableSeats")).intValue());
            if (payload.get("price") != null)
                showtime.setPrice(((Number) payload.get("price")).doubleValue());

            return ResponseEntity.ok(showtimeService.updateShowtime(id, showtime, movieId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating showtime: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }
}