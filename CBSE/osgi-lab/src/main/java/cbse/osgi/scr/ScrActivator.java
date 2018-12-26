package cbse.osgi.scr;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.BundleTracker;

public class ScrActivator implements BundleActivator {
    private BundleTracker<Bundle> bundleTracker;

    public void start(BundleContext context) {
        bundleTracker = new ScrBundleTracker(context);
        bundleTracker.open();
    }

    public void stop(BundleContext context) {
        bundleTracker.close();
    }
}
