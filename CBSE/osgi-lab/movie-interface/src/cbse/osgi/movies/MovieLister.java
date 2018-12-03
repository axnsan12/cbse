package cbse.osgi.movies;

import java.util.List;

public interface MovieLister {
    List<Movie> moviesDirectedBy(String arg);
}
