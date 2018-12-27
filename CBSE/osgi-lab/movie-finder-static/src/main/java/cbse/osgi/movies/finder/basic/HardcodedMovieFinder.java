package cbse.osgi.movies.finder.basic;

import cbse.osgi.movies.Movie;
import cbse.osgi.movies.MovieFinder;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import java.util.Arrays;
import java.util.List;

@Component
@Provides
@Instantiate
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
