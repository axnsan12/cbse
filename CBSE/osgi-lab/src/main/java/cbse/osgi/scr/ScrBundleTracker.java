package cbse.osgi.scr;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.ServiceTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScrBundleTracker extends BundleTracker<Bundle> {
    private final BundleContext context;
    private final ScrResolver resolver;
    private final List<ScrServiceTracker> serviceTrackers = new ArrayList<>();

    public ScrBundleTracker(BundleContext context, ScrResolver resolver) {
        super(context, Bundle.ACTIVE, null);
        this.context = context;
        this.resolver = resolver;
    }

    private String readBundleFile(Bundle bundle, String pathInBundle) throws IOException {
        URL entry = bundle.getEntry(pathInBundle);
        if (entry == null) {
            throw new RuntimeException(pathInBundle + " does not exist in " + bundle.getSymbolicName());
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entry.openStream()))) {
            return reader.lines().collect(Collectors.joining(" "));
        }
    }

    private static final Pattern implRegex = Pattern.compile("<(?:scr:)?implementation .*?class=\"(.+?)\".*?/>");
    private static final Pattern provideRegex = Pattern.compile("<(?:scr:)provide .*?interface=\"(.+?)\".*?/>");
    private static final Pattern refRegex = Pattern.compile(
            "<(?:scr:)reference .*?bind=\"(.+?)\" .*?unbind=\"(.+?)\" .*?interface=\"(.+?)\".*?/>");

    private Class<?> getImplClass(Bundle bundle, String componentPath, String componentConfig) throws ClassNotFoundException {
        Class<?> implClass = null;
        Matcher m = implRegex.matcher(componentConfig);
        while (m.find()) {
            if (implClass == null) {
                implClass = bundle.loadClass(m.group(1));
            } else {
                throw new RuntimeException("multiple implmenetation classes defined in component" + componentPath);
            }
        }

        if (implClass == null) {
            throw new RuntimeException("no implementation class defined for component " + componentPath);
        }

        return implClass;
    }

    private List<Class<?>> getProviders(Bundle bundle, String componentConfig) throws ClassNotFoundException {
        ArrayList<Class<?>> provided = new ArrayList<>();
        Matcher m = provideRegex.matcher(componentConfig);
        while (m.find()) {
            provided.add(bundle.loadClass(m.group(1)));
        }
        return provided;
    }

    private List<ScrComponent.ScrReference> getReferences(Bundle bundle, String componentConfig, Class<?> implClass) throws NoSuchMethodException, ClassNotFoundException {
        ArrayList<ScrComponent.ScrReference> references = new ArrayList<>();
        Matcher m = refRegex.matcher(componentConfig);

        while (m.find()) {
            Class<?> serviceClass = bundle.loadClass(m.group(3));
            String bindMethodName = m.group(1);
            String unbindMehodName = m.group(2);
            references.add(new ScrComponent.ScrReference(implClass, serviceClass, bindMethodName, unbindMehodName));
        }

        return references;
    }

    private List<ScrComponent> getComponents(Bundle bundle) {
        String componentsCsv = bundle.getHeaders().get("Service-Component");
        ArrayList<ScrComponent> components = new ArrayList<>();

        if (componentsCsv == null || componentsCsv.isEmpty()) {
            System.out.println("--- Bundle " + bundle.getSymbolicName() + " has no Service-Component manifest header");
            return components;
        }

        for (String componentPath : componentsCsv.split("\\s*,\\s*")) {
            try {
                String componentConfig = readBundleFile(bundle, componentPath);
                Class<?> implClass = getImplClass(bundle, componentPath, componentConfig);
                List<Class<?>> provided = getProviders(bundle, componentConfig);
                List<ScrComponent.ScrReference> referenced = getReferences(bundle, componentConfig, implClass);

                ScrComponent component = new ScrComponent(bundle, implClass, provided, referenced);
                components.add(component);
                System.out.println("--- Parsed component config " + component);
            } catch (Throwable t) {
                System.err.println("--- Error reading config file " + componentPath);
                t.printStackTrace();
            }
        }

        return components;
    }


    @Override
    public Bundle addingBundle(Bundle bundle, BundleEvent event) {
        List<ScrComponent> components = getComponents(bundle);
        components.forEach(resolver::addComponent);
        resolver.tryResolve();
        components.forEach(c -> c.requiredServices.forEach(ref -> serviceTrackers.add(new ScrServiceTracker(context, ref.serviceClass, resolver))));
        return bundle;
    }

    @Override
    public void modifiedBundle(Bundle bundle, BundleEvent event, Bundle object) {

    }

    @Override
    public void removedBundle(Bundle bundle, BundleEvent event, Bundle trackedObject) {
        resolver.removedBundle(bundle);
    }

    @Override
    public void close() {
        serviceTrackers.forEach(ServiceTracker::close);
        super.close();
    }
}
