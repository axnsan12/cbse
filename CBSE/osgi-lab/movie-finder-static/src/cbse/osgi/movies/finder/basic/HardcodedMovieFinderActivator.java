package cbse.osgi.movies.finder.basic;

import cbse.osgi.movies.MovieFinder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class HardcodedMovieFinderActivator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(BundleContext context) {
        if (context.getBundle().getHeaders().get("Movie-Finder-Class") == null) {
            MovieFinder finder = new HardcodedMovieFinder();
            registration = context.registerService(MovieFinder.class, finder, null);
        }
    }

    public void stop(BundleContext context) {
        if (registration != null) {
            registration.unregister();
        }
    }
}
