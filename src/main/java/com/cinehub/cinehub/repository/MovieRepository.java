package com.cinehub.cinehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinehub.cinehub.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
