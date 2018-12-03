package cbse.osgi.movies.finder.json;

import cbse.osgi.movies.MovieFinder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class JsonMovieFinderActivator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(BundleContext context) {
        JsonMovieFinder finder = new JsonMovieFinder(context.getBundle().getResource("movies.json"));
        registration = context.registerService(MovieFinder.class, finder, null);
    }

    public void stop(BundleContext context) {
        registration.unregister();
    }
}
