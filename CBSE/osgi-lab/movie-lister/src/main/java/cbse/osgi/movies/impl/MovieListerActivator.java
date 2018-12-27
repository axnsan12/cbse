package cbse.osgi.movies.impl;

import cbse.osgi.movies.MovieFinder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.ServiceTracker;

public class MovieListerActivator implements BundleActivator {
    private ServiceTracker<MovieFinder, MovieFinder> serviceTracker;
    private BundleTracker<Bundle> bundleTracker;

    public void start(BundleContext context) {
        serviceTracker = new ServiceTracker<>(context, MovieFinder.class.getName(), new MovieFinderServiceTracker(context));
        serviceTracker.open();
        bundleTracker = new MovieFinderBundleTracker(context);
        bundleTracker.open();
    }

    public void stop(BundleContext context) {
        bundleTracker.close();
        serviceTracker.close();
    }
}
