package com.cinehub.cinehub.controller;

import com.cinehub.cinehub.model.Booking;
import com.cinehub.cinehub.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:8080")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ✅ Get all bookings or filter by user email
    @GetMapping
    public List<Booking> getBookings(@RequestParam(required = false) String userEmail) {
        if (userEmail != null && !userEmail.isEmpty()) {
            return bookingService.getBookingsByUser(userEmail);
        }
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, Object> request) {
        try {
            Long movieId = ((Number) request.get("movieId")).longValue();
            String userEmail = (String) request.get("userEmail");
            Integer seats = (Integer) request.get("seats");
            Double totalPrice = ((Number) request.get("totalPrice")).doubleValue();

            Booking booking = bookingService.createBooking(movieId, userEmail, seats, totalPrice);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
