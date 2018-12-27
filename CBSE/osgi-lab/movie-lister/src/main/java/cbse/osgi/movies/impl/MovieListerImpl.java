package cbse.osgi.movies.impl;

import cbse.osgi.movies.Movie;
import cbse.osgi.movies.MovieFinder;
import cbse.osgi.movies.MovieLister;
import org.apache.felix.ipojo.annotations.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Provides
@Instantiate
public class MovieListerImpl implements MovieLister {
    private Collection<MovieFinder> finders = Collections.synchronizedCollection(new ArrayList<>());
    private String director = "director2";

    @Bind
    protected void bindFinder(MovieFinder finder) {
        finders.add(finder);
        System.out.println("MovieLister: added a finder " + finder);
        System.out.println("Movies directed by " + director + ": " + moviesDirectedBy(director));
    }

    @Unbind
    protected void unbindFinder(MovieFinder finder) {
        finders.remove(finder);
        System.out.println("MovieLister: removed a finder " + finder);
        System.out.println("Movies directed by " + director + ": " + moviesDirectedBy(director));
    }

    public List<Movie> moviesDirectedBy(String director) {
        List<Movie> result = new ArrayList<>();
        for (MovieFinder finder : new ArrayList<>(finders)) {
            result.addAll(finder.findAll().stream().filter(
                    (m) -> director.equals(m.getDirector())
            ).collect(Collectors.toList()));
        }
        return result;
    }

}