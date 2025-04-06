package com.moviecatalog.controller;

import com.moviecatalog.entity.Movie;
import com.moviecatalog.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // ADMIN ONLY: Create a new movie
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Movie createMovie(@RequestBody Movie movie, Principal principal) {
        movie.setCreatedBy(principal.getName());
        return movieService.saveMovie(movie);
    }

    // ADMIN ONLY: Update an existing movie
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    // ADMIN ONLY: Delete a movie
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    // USER and ADMIN: Get movies with search, category, year, etc.
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping
    public List<Movie> searchMovies(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return movieService.searchMovies(query, category, year, page, size, sortBy, direction);
    }
}
