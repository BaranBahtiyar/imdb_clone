package com.example.imdb_clone.controller;

import com.example.imdb_clone.dto.MovieDto;
import com.example.imdb_clone.dto.TmdbMovieDto;
import com.example.imdb_clone.service.MovieService;
import com.example.imdb_clone.service.TmdbService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final TmdbService tmdbService;
    private final MovieService movieService;

    @GetMapping("/popular")
    public List<TmdbMovieDto> getPopularMovies() {
        return  tmdbService.fetchPopularMovies();
    }

    @PostMapping("/save-popular")
    public ResponseEntity<Map<String, Object>> savePopularMovies() {
        int savedCount = movieService.saveMoviesFromTmdb(tmdbService.fetchPopularMovies());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Filmler veri tabanına kaydedildi");
        response.put("saved count", savedCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieDto movieDto) {
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid MovieDto movieDto) {
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return ResponseEntity.ok(createdMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}


