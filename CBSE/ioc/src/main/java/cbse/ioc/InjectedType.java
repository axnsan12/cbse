package cbse.ioc;

import java.util.Arrays;
import java.util.Objects;

public class InjectedType<T> {
    final Class<T> implClass;
    final Object[] params;

    public InjectedType(Class<T> implClass, Object[] params) {
        this.implClass = Objects.requireNonNull(implClass);
        this.params = params != null ? params : new Object[0];
    }

    public InjectedType(Class<T> implClass) {
        this.implClass = Objects.requireNonNull(implClass);
        this.params = new Object[0];
    }

    @Override
    public String toString() {
        return "InjectedType{" +
                "implClass=" + implClass +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
