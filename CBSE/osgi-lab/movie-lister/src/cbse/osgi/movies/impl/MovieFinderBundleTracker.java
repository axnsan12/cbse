package cbse.osgi.movies.impl;

import cbse.osgi.movies.MovieFinder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

public class MovieFinderBundleTracker extends BundleTracker<Bundle> {
    public MovieFinderBundleTracker(BundleContext context) {
        super(context, Bundle.ACTIVE, null);
    }

    @Override
    public Bundle addingBundle(Bundle bundle, BundleEvent event) {
        String className = bundle.getHeaders().get("Movie-Finder-Class");
        if (className != null) {
            Class<?> clazz;
            try {
                clazz = bundle.loadClass(className);
                try {
                    MovieFinder finder = (MovieFinder) clazz.newInstance();
                    System.out.println("Registering MovieFinder service for: " + clazz.getName());
                    bundle.getBundleContext().registerService(MovieFinder.class, finder, null);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }
}
