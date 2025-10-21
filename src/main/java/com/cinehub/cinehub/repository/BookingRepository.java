package com.cinehub.cinehub.repository;

import com.cinehub.cinehub.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
