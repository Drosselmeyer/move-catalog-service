package com.moviecatalog.dto;

import java.time.LocalDateTime;

public class MovieDTO {
    public Long id;
    public String name;
    public String synopsis;
    public Integer releaseYear;
    public String imageUrl;
    public String category;
    public LocalDateTime createdAt;
    public Double averageRating;
}
