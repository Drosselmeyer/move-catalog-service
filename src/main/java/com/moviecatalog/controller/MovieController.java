package com.moviecatalog.controller;

import javax.validation.Valid;
import com.moviecatalog.model.Movie;
import com.moviecatalog.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                movieService.searchMovies(query, category, year, page, size, sortBy, direction)
        );
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.saveMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updated = movieService.updateMovie(id, movie);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ADMIN ONLY ENDPOINTS
    @PostMapping("/admin")
    public ResponseEntity<Movie> adminCreateMovie(@RequestBody @Valid Movie movie) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(movieService.saveMovie(movie));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Movie> adminUpdateMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        Movie updated = movieService.updateMovie(id, movie);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> adminDeleteMovie(@PathVariable Long id) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UTILITY: Dummy method to simulate auth for now
    private boolean isAdmin() {
        // Simulate admin check (replace with real security later)
        return true;
    }
}
