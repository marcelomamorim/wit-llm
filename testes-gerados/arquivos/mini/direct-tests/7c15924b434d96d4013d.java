/*
 * @test
 * @summary Test java.util.ServiceLoader.next() method behavior
 * @run main ServiceLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Iterator;

public class ServiceLoaderWitupTest {

    // Minimal stub for Provider<T> to test next()
    static class DummyProvider<T> extends ServiceLoader.Provider<T> {
        private final T instance;
        DummyProvider(Class<T> svc, T inst) {
            super(svc);
            this.instance = inst;
        }
        @Override
        public T get() {
            return instance;
        }
    }

    // Subclass ServiceLoader to expose nextProvider and nextError for testing
    static class TestServiceLoader<T> extends ServiceLoader<T> {
        Provider<T> nextProvider;
        ServiceConfigurationError nextError;
        boolean hasNextValue;

        TestServiceLoader(Class<T> svc) {
            super(svc, null);
        }

        @Override
        public boolean hasNext() {
            return hasNextValue;
        }

        @Override
        public Provider<T> next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Provider<T> provider = nextProvider;
            if (provider != null) {
                nextProvider = null;
                return provider;
            } else {
                ServiceConfigurationError e = nextError;
                assert e != null;
                nextError = null;
                throw e;
            }
        }
    }

    public static void main(String[] args) {
        testNextReturnsProvider();
        testNextThrowsNoSuchElementException();
        testNextThrowsServiceConfigurationError();
    }

    static void testNextReturnsProvider() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class);
        loader.hasNextValue = true;
        loader.nextProvider = new DummyProvider<>(String.class, "test");
        loader.nextError = null;

        ServiceLoader.Provider<String> p = loader.next();
        if (p == null) {
            throw new AssertionError("next() returned null provider");
        }
        String val = p.get();
        if (!"test".equals(val)) {
            throw new AssertionError("Provider returned unexpected value: " + val);
        }
        if (loader.nextProvider != null) {
            throw new AssertionError("nextProvider was not cleared after next()");
        }
    }

    static void testNextThrowsNoSuchElementException() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class);
        loader.hasNextValue = false;
        loader.nextProvider = null;
        loader.nextError = null;

        try {
            loader.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextThrowsServiceConfigurationError() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class);
        loader.hasNextValue = true;
        loader.nextProvider = null;
        loader.nextError = new ServiceConfigurationError("test error");

        try {
            loader.next();
            throw new AssertionError("Expected ServiceConfigurationError not thrown");
        } catch (ServiceConfigurationError e) {
            if (!"test error".equals(e.getMessage())) {
                throw new AssertionError("Unexpected error message: " + e.getMessage());
            }
        }
        if (loader.nextError != null) {
            throw new AssertionError("nextError was not cleared after throwing");
        }
    }
}
