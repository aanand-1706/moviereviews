package com.aanand.demo.controllers;

import com.aanand.demo.Movie;
import com.aanand.demo.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public List<Movie> getAllMovies(){
//        List<Movie> list = new ArrayList<>();
//        list.add(new Movie(1, "Aavesham", "Jithu Madhavan"));
//        list.add(new Movie(2, "Patriot", "Mahesh Narayanan"));
//        return list;
        return movieService.getAllMovies();
    }

    @PostMapping("/")
    public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
    }
}
