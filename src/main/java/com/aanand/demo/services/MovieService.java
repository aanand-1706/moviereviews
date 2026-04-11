package com.aanand.demo.services;

import com.aanand.demo.Movie;
import com.aanand.demo.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private final WebClient linqWebClient;

    public MovieService(WebClient linqWebClient, MovieRepository movieRepository) {
        this.linqWebClient = linqWebClient;
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> fetchAllMovies(){
        return movieRepository.findAll();
    }
}
