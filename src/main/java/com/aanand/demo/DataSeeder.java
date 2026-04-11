package com.aanand.demo;

import com.aanand.demo.repositories.MovieRepository;
import com.aanand.demo.repositories.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class DataSeeder implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;

    public DataSeeder(MovieRepository movieRepository, SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {
        List<Movie> movies = movieRepository.saveAll(List.of(
                new Movie("Anwar", "Amal Neerad"),
                new Movie("Aavesham", "Jithu Madhavan"),
                new Movie("Gold", "Alphonse Puthren"),
                new Movie("Patriot", "Mahesh Narayanan")
        ));
        System.out.println("Seeded " + movieRepository.count() + " movies");

        List<Seat> seats = new ArrayList<>();
        for (Movie movie : movies) {
            for (int i = 1; i <= 10; i++) {
                seats.add(new Seat(movie.getId(), i, false));
            }
        }
        seatRepository.saveAll(seats);
        System.out.println("Seeded " + seatRepository.count() + " seats");
    }
}
