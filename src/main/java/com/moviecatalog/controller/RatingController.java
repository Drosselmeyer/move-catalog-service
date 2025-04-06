import com.moviecatalog.entity.Rating;
import com.moviecatalog.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/{movieId}")
    public Rating rateMovie(@PathVariable Long movieId, @RequestParam int score, @AuthenticationPrincipal UserDetails user) {
        return ratingService.rateMovie(movieId, user.getUsername(), score);
    }

    @DeleteMapping("/{movieId}")
    public void deleteRating(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails user) {
        ratingService.deleteRating(movieId, user.getUsername());
    }

    @GetMapping("/me")
    public List<Rating> getUserRatings(@AuthenticationPrincipal UserDetails user) {
        return ratingService.getRatingsByUser(user.getUsername());
    }
}
