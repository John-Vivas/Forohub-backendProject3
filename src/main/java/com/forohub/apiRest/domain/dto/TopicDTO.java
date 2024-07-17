package com.forohub.apiRest.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicDTO (
    Long id,
    @NotBlank
    String title,
    @NotBlank
    String message,
    @NotNull
    @JsonAlias("createDate")
    String createDate,
    @NotNull
    int status,
    @NotBlank
    String author,
    @NotBlank
    String course,
    String answers) {
}
