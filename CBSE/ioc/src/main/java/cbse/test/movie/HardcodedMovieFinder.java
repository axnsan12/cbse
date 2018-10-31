package cbse.test.movie;

import java.util.Arrays;
import java.util.List;

public class HardcodedMovieFinder implements MovieFinder {
    @Override
    public List<Movie> findAll() {
        return Arrays.asList(
                new Movie("hc-movie1", "director1"),
                new Movie("hc-movie2", "director2"),
                new Movie("hc-movie3", "director2"),
                new Movie("hc-movie4", "director3")
        );
    }
}
