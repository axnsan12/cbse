package cbse.osgi.scr;

import org.osgi.framework.*;
import org.osgi.util.tracker.BundleTracker;

public class ScrActivator implements BundleActivator {
    private BundleTracker<Bundle> bundleTracker;
    private ScrResolver resolver = new ScrResolver();

    public void start(BundleContext context) {
        try {
            context.getServiceReferences(Object.class, null);
            bundleTracker = new BundleTracker<>(context, Bundle.ACTIVE, new ScrBundleTracker(context, resolver));
            bundleTracker.open();
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void stop(BundleContext context) {
        bundleTracker.close();
    }
}
