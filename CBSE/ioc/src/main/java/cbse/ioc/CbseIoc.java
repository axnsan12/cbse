package cbse.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class CbseIoc {
    private final InjectorConfig config;

    public CbseIoc(InjectorConfig config) {
        this.config = config;
    }

    public InjectorConfig getConfig() {
        return config;
    }

    @SuppressWarnings("unchecked")
    public <T, R extends T> R getInstance(Class<T> interfaceClass) {
        InjectedType<T> type = config.get(interfaceClass);
        if (type == null) {
            throw new IllegalStateException(interfaceClass + " is not registered to any type");
        }

        for (Constructor<?> ctor : type.implClass.getDeclaredConstructors()) {
            if (canUseConstructor(ctor, type.params)) {
                Class<?>[] injectableTypes = getInjectableParamTypes(ctor.getParameterTypes(), type.params.length);
                Stream<Object> injectedParams = Stream.of(Objects.requireNonNull(injectableTypes)).map(this::getInstance);
                try {
                    return (R) ctor.newInstance(Stream.concat(Stream.of(type.params), injectedParams).toArray());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException("failed to instantiate " + type, e);
                }
            }
        }

        throw new IllegalStateException("failed to find usable constructor for " + type);
    }

    /**
     * Check if the parameter values given by *params* can be used to call a function which accepts parameters
     * given by *paramTypes*.
     */
    private boolean paramTypesMatch(Class<?>[] paramTypes, Object[] params) {
        if (params.length != paramTypes.length) {
            return false;
        }

        for (int i = 0; i < paramTypes.length; ++i) {
            if (params[i] == null) {
                // null can be assigned to anything
                continue;
            }

            if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
                return false;
            }
        }

        return true;
    }

    private Class<?>[] getInjectableParamTypes(Class<?>[] ctorParamTypes, int fixedParamCount) {
        if (fixedParamCount > ctorParamTypes.length) {
            return null;
        }

        Class<?>[] injectableParamTypes = Arrays.copyOfRange(ctorParamTypes, fixedParamCount, ctorParamTypes.length);
        for (Class<?> paramType : injectableParamTypes) {
            if (config.get(paramType) == null) {
                return null;
            }
        }

        return injectableParamTypes;
    }

    private boolean canUseConstructor(Constructor<?> ctor, Object[] fixedParams) {
        // a constructor can be used to instantiate an object if:
        //  * it takes no parameters,
        //  * or we can inject all parameters,
        //  * or it takes exactly fixedParams.length parameters,
        //  * or it takes at least fixedParams.length parameters and all parameters not bound to values can be injected

        fixedParams = fixedParams != null ? fixedParams : new Object[0];
        Class<?>[] ctorParamTypes = ctor.getParameterTypes();
        if (ctorParamTypes.length < fixedParams.length) {
            return false;
        }

        Class<?>[] fixedParamTypes = Arrays.copyOf(ctorParamTypes, fixedParams.length);
        if (!paramTypesMatch(fixedParamTypes, fixedParams)) {
            return false;
        }

        return getInjectableParamTypes(ctorParamTypes, fixedParams.length) != null;
    }
}
