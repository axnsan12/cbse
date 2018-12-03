package cbse.osgi.movies.impl;

import cbse.osgi.movies.MovieFinder;
import cbse.osgi.movies.MovieLister;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.concurrent.atomic.AtomicInteger;

public class MovieListerActivator implements BundleActivator {
    private ServiceTracker<MovieFinder, MovieFinder> serviceTracker;

    class MovieFinderTracker implements ServiceTrackerCustomizer<MovieFinder, MovieFinder>
    {
        private final BundleContext context;
        private MovieListerImpl movieLister = new MovieListerImpl();
        private AtomicInteger finderCount = new AtomicInteger(0);
        private volatile ServiceRegistration<MovieLister> listerRegistration;
        private volatile boolean pendingRegistration;

        MovieFinderTracker(BundleContext context) {
            this.context = context;
        }

        @Override
        public MovieFinder addingService(ServiceReference<MovieFinder> reference) {
            MovieFinder finder = context.getService(reference);
            movieLister.bindFinder(finder);
            synchronized (this) {
                if (pendingRegistration || finderCount.incrementAndGet() != 1) {
                    return finder;
                }

                pendingRegistration = true;
            }

            listerRegistration = context.registerService(MovieLister.class, movieLister, null);

            synchronized (this) {
                pendingRegistration = false;
            }
            return finder;
        }

        @Override
        public void modifiedService(ServiceReference<MovieFinder> reference, MovieFinder service) {

        }

        @Override
        public void removedService(ServiceReference<MovieFinder> reference, MovieFinder service) {
            movieLister.unbindFinder(service);
            context.ungetService(reference);

            ServiceRegistration<MovieLister> registration = null;
            synchronized (this) {
                if (finderCount.decrementAndGet() == 0) {
                    registration = listerRegistration;
                    listerRegistration = null;
                }
            }

            if (registration != null) {
                registration.unregister();
            }
        }
    }

    public void start(BundleContext context) {
        serviceTracker = new ServiceTracker<>(context, MovieFinder.class.getName(), new MovieFinderTracker(context));
        serviceTracker.open();
    }

    public void stop(BundleContext context) {
        serviceTracker.close();
    }
}
