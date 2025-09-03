package com.example.imdb_clone.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TmdbMovieDto {
    private Long id;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
    private Set<Long> categoryIds;

}
