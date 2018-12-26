package cbse.osgi.scr;

import org.osgi.framework.Bundle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ScrComponent {
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

    public final Bundle bundle;
    public final Class<?> implClass;
    public final List<Class<?>> serviceClasses;
    public final List<ScrReference> requiredServices;

    private static <T> List<T> join(List<T> a, List<T> b) {
        List<T> result = new ArrayList<>(a);
        result.addAll(b);
        return Collections.unmodifiableList(result);
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
        this.requiredServices = join(c2.requiredServices, c2.requiredServices);
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

    public static class ScrReference {
        public final Class<?> serviceClass;
        public final Method bindMethod;
        public final Method unbindMethod;

        public ScrReference(Class<?> implClass, Class<?> serviceClass, String bindMethodName, String unbindMethodName) throws NoSuchMethodException {
            this.serviceClass = Objects.requireNonNull(serviceClass);
            this.bindMethod = implClass.getDeclaredMethod(bindMethodName, serviceClass);
            this.unbindMethod = implClass.getDeclaredMethod(unbindMethodName, serviceClass);
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
