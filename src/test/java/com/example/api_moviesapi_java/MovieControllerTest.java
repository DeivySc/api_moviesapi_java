package com.example.api_moviesapi_java;

import com.example.api_moviesapi_java.model.Movie;
import com.example.api_moviesapi_java.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setGenre("Sci-Fi");
        movie.setYear(2010);
    }

    @Test
    void testGetAllMovies() throws Exception {
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[0].genre").value("Sci-Fi"))
                .andExpect(jsonPath("$[0].year").value(2010));

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetMovieById() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.year").value(2010));

        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateMovie() throws Exception {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Inception\",\"genre\":\"Sci-Fi\",\"year\":2010}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.year").value(2010));

        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie() throws Exception {
        Movie updatedMovie = new Movie();
        updatedMovie.setId(1L);
        updatedMovie.setTitle("The Dark Knight");
        updatedMovie.setGenre("Action");
        updatedMovie.setYear(2008);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/api/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"The Dark Knight\",\"genre\":\"Action\",\"year\":2008}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.genre").value("Action"))
                .andExpect(jsonPath("$.year").value(2008));

        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testDeleteMovie() throws Exception {
        doNothing().when(movieRepository).deleteById(1L);

        mockMvc.perform(delete("/api/movies/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(movieRepository, times(1)).deleteById(1L);
    }
}
