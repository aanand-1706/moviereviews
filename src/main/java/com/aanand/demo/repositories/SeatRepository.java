package com.aanand.demo.repositories;

import com.aanand.demo.Movie;
import com.aanand.demo.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByMovieIdAndIsBooked(Integer movieId, boolean isBooked);

    Seat findByMovieIdAndSeatNo(Integer movieId, Integer seatNo);
}
