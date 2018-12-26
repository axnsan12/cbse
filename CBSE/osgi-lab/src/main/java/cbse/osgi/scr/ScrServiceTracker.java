package cbse.osgi.scr;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class ScrServiceTracker extends ServiceTracker<Object, Object>
{
    private final BundleContext context;
    private final ScrResolver resolver;

    @SuppressWarnings("unchecked")
    ScrServiceTracker(BundleContext context, Class<?> serviceClass, ScrResolver resolver) {
        super(context, (Class<Object>) serviceClass, null);
        this.context = context;
        this.resolver = resolver;
        open();
    }

    @Override
    public Object addingService(ServiceReference<Object> reference) {
        System.out.println("----- addingService " + reference);
        Object service = context.getService(reference);
        resolver.tryResolve();
        return service;
    }

    @Override
    public void modifiedService(ServiceReference<Object> reference, Object service) {

    }

    @Override
    public void removedService(ServiceReference<Object> reference, Object service) {
        System.out.println("----- removedService " + reference + " " + service);
        resolver.removedService(service);
    }
}
