package cbse.ioc;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class InjectorConfig {
    @SuppressWarnings("unchecked")
    public static InjectorConfig fromJson(String configFileName) throws FileNotFoundException, ClassNotFoundException {
        JsonReader json = new JsonReader(new FileReader(configFileName));
        Map<String, Object> configMap = new Gson().fromJson(json, Map.class);
        InjectorConfig config = new InjectorConfig();

        for (Map.Entry<String, Object> e1 : configMap.entrySet()) {
            Map<String, Object> injectedType = (Map<String, Object>) e1.getValue();
            Class interfaceClass = Class.forName(e1.getKey());
            Class implClass = Class.forName((String) injectedType.get("impl"));
            Object[] params = new Object[0];
            if (injectedType.containsKey("params")) {
                params = ((ArrayList) Objects.requireNonNull(injectedType.get("params"))).toArray();
            }
            config.registerImpl(interfaceClass, implClass, params);
        }

        return config;
    }

    private final Map<Class<?>, InjectedType<?>> implMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T, R extends T> InjectedType<R> get(Class<T> interfaceClass) {
        return (InjectedType<R>) implMap.get(interfaceClass);
    }

    public Map<Class<?>, InjectedType<?>> getAll() {
        return Collections.unmodifiableMap(implMap);
    }

    public <T> InjectorConfig registerImpl(Class<T> interfaceClass, InjectedType<? extends T> impl) {
        InjectedType<?> existing = implMap.getOrDefault(Objects.requireNonNull(interfaceClass), null);
        if (existing != null) {
            throw new IllegalArgumentException(interfaceClass + " is already registered as " + existing);
        }
        if (!interfaceClass.isAssignableFrom(impl.implClass)) {
            throw new IllegalArgumentException(impl.implClass + " is not a subclass of " + interfaceClass);
        }
        this.implMap.put(interfaceClass, Objects.requireNonNull(impl));
        return this;
    }


    public <T> InjectorConfig registerImpl(Class<T> interfaceClass, Class<? extends T> implClass, Object[] params) {
        return registerImpl(interfaceClass, new InjectedType<>(implClass, params));
    }

    public <T> InjectorConfig registerImpl(Class<T> interfaceClass, Class<? extends T> implClass) {
        return registerImpl(interfaceClass, new InjectedType<>(implClass));
    }

    public <T> InjectorConfig registerImpl(Class<T> implClass, Object[] params) {
        return registerImpl(implClass, implClass, params);
    }

    public <T> InjectorConfig registerImpl(Class<T> implClass) {
        return registerImpl(implClass, implClass);
    }
}
