package com.moviecatalog.service;

import com.moviecatalog.entity.Rating;
import java.util.List;

public interface RatingService {
    Rating rateMovie(Long movieId, String userEmail, int score);
    void deleteRating(Long movieId, String userEmail);
    List<Rating> getRatingsByUser(String userEmail);
}


