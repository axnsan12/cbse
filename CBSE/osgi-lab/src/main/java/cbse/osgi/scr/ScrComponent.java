package cbse.osgi.scr;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.lang.reflect.Method;
import java.util.*;

public class ScrComponent {
    public final Bundle bundle;
    public final Class<?> implClass;
    public final List<Class<?>> serviceClasses;
    public final List<ScrReference> requiredServices;

    private boolean resolved = false;
    private Object instance = null;
    private final List<ServiceRegistration> registrations = new ArrayList<>();

    public ScrComponent(Bundle bundle, Class<?> implClass, List<Class<?>> serviceClasses, List<ScrReference> requiredServices) {
        this.bundle = Objects.requireNonNull(bundle);
        this.implClass = Objects.requireNonNull(implClass);
        this.serviceClasses = Collections.unmodifiableList(new ArrayList<>(serviceClasses));
        this.requiredServices = Collections.unmodifiableList(new ArrayList<>(requiredServices));

        for (Class<?> service : serviceClasses) {
            if (!service.isAssignableFrom(implClass)) {
                throw new IllegalArgumentException(implClass + " does not implement service interface " + service + " which it wants to provide");
            }
        }
    }

    public ScrComponent(ScrComponent c1, ScrComponent c2) {
        if (!c1.implClass.equals(c2.implClass)) {
            throw new IllegalArgumentException("Cannot merge components with differing implClass! " + c1 + " " + c2);
        }
        if (!c1.bundle.equals(c2.bundle)) {
            throw new IllegalArgumentException("Cannot merge components from different bundles! " + c1 + " " + c2);
        }

        this.bundle = c1.bundle;
        this.implClass = c1.implClass;
        this.serviceClasses = join(c1.serviceClasses, c2.serviceClasses);
        this.requiredServices = join(c1.requiredServices, c2.requiredServices);
    }

    public boolean isResolved() {
        return resolved;
    }

    protected boolean refsSatisfied() {
        BundleContext ctx = bundle.getBundleContext();
        return requiredServices.stream().allMatch(ref -> ctx.getServiceReference(ref.serviceClass) != null);
    }

    private boolean resolving = false;

    public boolean tryResolve() {
        if (isResolved() || resolving) {
            return false;
        }

        if (!refsSatisfied()) {
            return false;
        }

        resolving = true;

        try {
            Object instance = implClass.newInstance();
            this.instance = instance;

            BundleContext ctx = bundle.getBundleContext();
            for (ScrReference ref : requiredServices) {
                ref.bind(instance, ctx);
            }
            for (Class<?> serviceClass : serviceClasses) {
                registrations.add(ctx.registerService((Class) serviceClass, instance, null));
            }

            this.resolved = true;
            System.out.println("=== Resolved component " + this);
        } catch (Exception e) {
            e.printStackTrace();
            destroy();
        } finally {
            resolving = false;
        }

        return resolved;
    }

    public void removedService(Object service) {
        if (requiredServices.stream().anyMatch(ref -> service == ref.getBoundService())) {
            destroy();
            tryResolve();
        }
    }

    protected void destroy() {
        if (instance == null) {
            resolved = false;
            return;
        }

        BundleContext ctx = bundle.getBundleContext();
        requiredServices.forEach(ref -> ref.unbind(instance, ctx));
        registrations.forEach(reg -> {
            try {
                reg.unregister();
            } catch (Exception e) {
                // pass
            }
        });
        registrations.clear();

        if (resolved) {
            System.out.println("=== Destroyed previously resolved component " + this);
        } else {
            System.out.println("=== Cleaned up partially resolved component " + this);
        }
        instance = null;
        resolved = false;
    }

    @Override
    public String toString() {
        return "ScrComponent{" +
                "bundle=" + bundle.getSymbolicName() +
                ", implClass=" + implClass +
                ", serviceClasses=" + serviceClasses +
                ", requiredServices=" + requiredServices +
                '}';
    }

    private static <T> List<T> join(List<T> a, List<T> b) {
        List<T> result = new ArrayList<>(a);
        result.addAll(b);
        return Collections.unmodifiableList(result);
    }

    public static class ScrReference {
        public final Class<?> serviceClass;
        public final Method bindMethod;
        public final Method unbindMethod;

        private ServiceReference boundRef;
        private Object boundService;

        public ScrReference(Class<?> implClass, Class<?> serviceClass, String bindMethodName, String unbindMethodName) throws NoSuchMethodException {
            this.serviceClass = Objects.requireNonNull(serviceClass);
            this.bindMethod = implClass.getDeclaredMethod(bindMethodName, serviceClass);
            this.unbindMethod = implClass.getDeclaredMethod(unbindMethodName, serviceClass);
        }

        public Object getBoundService() {
            return boundService;
        }

        public void bind(Object impl, BundleContext context) throws Exception {
            ServiceReference<?> ref = context.getServiceReference(serviceClass);
            Object service = context.getService(ref);
            boundRef = ref;

            bindMethod.setAccessible(true);
            bindMethod.invoke(impl, service);
            boundService = service;
            System.out.println("+++ Bound " + service + " to " + ref);
        }

        public void unbind(Object impl, BundleContext context) {
            if (boundService == null && boundRef == null) {
                return;
            }

            try {
                if (boundRef != null) {
                    context.ungetService(boundRef);
                }

                if (boundService != null) {
                    unbindMethod.setAccessible(true);
                    unbindMethod.invoke(impl, boundService);
                }

                System.out.println("+++ Unbound " + boundRef + "/" + boundService + " from " + this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            boundRef = null;
            boundService = null;
        }

        @Override
        public String toString() {
            return "ScrReference{" +
                    "serviceClass=" + serviceClass +
                    ", bindMethod=" + bindMethod.getName() +
                    ", unbindMethod=" + unbindMethod.getName() +
                    '}';
        }
    }
}
