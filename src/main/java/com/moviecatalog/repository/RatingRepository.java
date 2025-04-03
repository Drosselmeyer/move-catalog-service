package com.moviecatalog.repository;

import com.moviecatalog.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {}
