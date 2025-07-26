package com.example.imdb_clone.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TmdbResponseDto {
    private int page;
    private List<TmdbMovieDto> results;
}
