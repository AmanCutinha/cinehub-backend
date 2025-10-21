package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.Booking;
import com.cinehub.cinehub.model.Movie;
import com.cinehub.cinehub.repository.BookingRepository;
import com.cinehub.cinehub.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Long movieId, String userEmail, Integer seats, Double totalPrice) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Booking booking = new Booking();
        booking.setMovie(movie);
        booking.setUserEmail(userEmail);
        booking.setSeats(seats);
        booking.setTotalPrice(totalPrice);
        booking.setBookingTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    
    public List<Booking> getBookingsByUser(String userEmail) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getUserEmail().equalsIgnoreCase(userEmail))
                .toList();
    }

}
