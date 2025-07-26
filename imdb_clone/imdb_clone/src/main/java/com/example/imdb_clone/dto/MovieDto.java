package com.example.imdb_clone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class MovieDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Başlık boş bırakılamaz.")
    private String title;

    @NotBlank(message = "Açıklama boş bırakılamaz.")
    private String overview;

    @NotBlank(message = "Poster URL boş bırakılamaz.")
    private String posterPath;

    @NotBlank(message = "Yayın tarih boş bırakılamaz.")
    private String releaseDate;

    @NotEmpty(message = "Kategori ID listesi boş bırakılamaz.")
    private Set<Long> categoryIds;

}
