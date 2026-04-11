package com.aanand.demo.services;

import com.aanand.demo.Seat;
import com.aanand.demo.repositories.MovieRepository;
import com.aanand.demo.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SeatService {
    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> fetchAvailableSeats(Integer movieId){
        return seatRepository.findByMovieIdAndIsBooked(movieId, false);
    }

    public Seat fetchSeatByMovieIdAndSeatNo(Integer movieId, Integer seatNo){
        return seatRepository.findByMovieIdAndSeatNo(movieId, seatNo);
    }

    public Seat bookSeat(Seat seat){
        seat.setBooked(true);
        return seatRepository.save(seat);
    }
}
