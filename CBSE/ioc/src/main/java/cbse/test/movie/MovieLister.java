package cbse.test.movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieLister {
    public MovieLister(MovieFinder finder) {
        this.finder = finder;
    }

    public List<Movie> moviesDirectedBy(String arg) {
        return finder.findAll().stream()
                .filter((movie -> movie.getDirector().equals(arg)))
                .collect(Collectors.toList());
    }

    private final MovieFinder finder;
}
