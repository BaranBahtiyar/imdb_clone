package com.example.imdb_clone.service;

import com.example.imdb_clone.dto.TmdbMovieDto;
import com.example.imdb_clone.dto.TmdbResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "512f6123bbaf0b0ee64fdb30c2018c9d";

    public List<TmdbMovieDto> fetchPopularMovies() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            TmdbResponseDto dto = mapper.readValue(response.getBody(), TmdbResponseDto.class);
            return dto.getResults();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    }
