package com.example.imdb_clone.service;

import com.example.imdb_clone.dto.MovieDto;
import com.example.imdb_clone.dto.TmdbMovieDto;
import com.example.imdb_clone.entity.Category;
import com.example.imdb_clone.entity.Movie;
import com.example.imdb_clone.exception.ResourceNotFoundException;
import com.example.imdb_clone.repository.CategoryRepository;
import com.example.imdb_clone.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {


    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    public MovieDto createMovie(MovieDto dto) {
        Movie movie = toEntity(dto);
        Movie saved = movieRepository.save(movie);
        return toDto(saved);
    }

    public int saveMoviesFromTmdb(List<TmdbMovieDto> tmdbMovieDtos) {
        int count = 0;
        for (TmdbMovieDto dto : tmdbMovieDtos) {
            Movie movie = new Movie();
            movie.setId(dto.getId());
            movie.setTitle(dto.getTitle());
            movie.setOverview(dto.getOverview());
            movie.setPoster_path(dto.getPoster_path());
            movie.setRelease_date(dto.getRelease_date());

            if (dto.getCategoryIds() != null) {
                Set<Category> categories = dto.getCategoryIds().stream()
                        .map(id -> categoryRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + id)))
                        .collect(Collectors.toSet());
                movie.setCategories(categories);
            }
            movieRepository.save(movie);
            count++;
        }
        return count;
    }

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film bulunamadı: " + id));
        return toDto(movie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Film zaten yok!");
        }
        movieRepository.deleteById(id);
    }

    public MovieDto updateMovie(Long id, MovieDto dto) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film bulunamadı: " + id));

        Movie updated = uptadeEntity(existing, dto);
        Movie saved = movieRepository.save(updated);
        return toDto(saved);
    }

    private MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setOverview(movie.getOverview());
        dto.setPosterPath(movie.getPoster_path());
        dto.setReleaseDate(movie.getRelease_date());

        Set<Long> categoryIds = movie.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        dto.setCategoryIds(categoryIds);

        return dto;
    }

    //create için
    private Movie toEntity(MovieDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setOverview(dto.getOverview());
        movie.setPoster_path(dto.getPosterPath());
        movie.setRelease_date(dto.getReleaseDate());

        Set<Category> categories = new HashSet<Category>();
        for (Long categoryId : dto.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamad<UNK>: " + categoryId));
            categories.add(category);
        }
        movie.setCategories(categories);

        return movie;
    }

    //uptade için
    public Movie uptadeEntity(Movie movie, MovieDto dto) {
        movie.setTitle(dto.getTitle());
        movie.setOverview(dto.getOverview());
        movie.setPoster_path(dto.getPosterPath());
        movie.setRelease_date(dto.getReleaseDate());

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : dto.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + categoryId));
            categories.add(category);
        }
        movie.setCategories(categories);

        return movie;
    }

}
