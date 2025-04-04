package com.moviecatalog.service;

import com.moviecatalog.model.Movie;
import com.moviecatalog.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id).map(movie -> {
            movie.setName(updatedMovie.getName());
            movie.setReleaseYear(updatedMovie.getReleaseYear());
            movie.setSynopsis(updatedMovie.getSynopsis());
            movie.setPosterUrl(updatedMovie.getPosterUrl());
            movie.setCategories(updatedMovie.getCategories());
            return movieRepository.save(movie);
        }).orElse(null);
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Movie> searchMovies(String query, String category, Integer year, int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        Specification<Movie> spec = (root, cq, cb) -> {
            var predicates = cb.conjunction();

            if (query != null && !query.isEmpty()) {
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("name")), "%" + query.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("synopsis")), "%" + query.toLowerCase() + "%")
                ));
            }

            if (category != null && !category.isEmpty()) {
                predicates = cb.and(predicates, cb.isMember(category, root.get("categories")));
            }

            if (year != null) {
                predicates = cb.and(predicates, cb.equal(root.get("releaseYear"), year));
            }

            return predicates;
        };

        return movieRepository.findAll(spec, pageable).getContent();
    }
}

