package com.example.api_moviesapi_java;

import com.example.api_moviesapi_java.model.Movie;
import com.example.api_moviesapi_java.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final MovieRepository movieRepository;

    public DataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        movieRepository.save(new Movie("Inception", "Sci-Fi", 2010));
        movieRepository.save(new Movie("The Dark Knight", "Action", 2008));
        movieRepository.save(new Movie("Interstellar", "Sci-Fi", 2014));
    }
}
