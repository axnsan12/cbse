package cbse.osgi.scr;

import org.osgi.framework.Bundle;

import java.util.HashMap;
import java.util.Map;

public class ScrResolver {
    private final Map<Class<?>, ScrComponent> componentsByImplClass = new HashMap<>();

    public void addComponent(ScrComponent component) {
        componentsByImplClass.merge(component.implClass, component, ScrComponent::new);
    }

    public void removedBundle(Bundle bundle) {
        componentsByImplClass.entrySet().removeIf(e -> bundle.equals(e.getValue().bundle));
    }

    public void removedService(Object service) {
        for (ScrComponent component : componentsByImplClass.values()) {
            component.removedService(service);
        }
    }

    public void tryResolve() {
        boolean resolvedAny;
        do {
            resolvedAny = false;
            for (ScrComponent component : componentsByImplClass.values()) {
                if (component.tryResolve()) {
                    resolvedAny = true;
                }
            }
        } while (resolvedAny);
    }
}
