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

    // Minimal subclass to access protected fields for testing
    static class TestServiceLoader<T> extends ServiceLoader<T> {
        Provider<T> nextProvider;
        ServiceConfigurationError nextError;

        TestServiceLoader(Class<T> svc, ClassLoader cl) {
            super(svc, cl);
        }

        @Override
        public Provider<T> next() {
            return super.next();
        }

        // Expose next() for testing with controlled state
        Provider<T> nextWithState(Provider<T> np, ServiceConfigurationError ne) {
            this.nextProvider = np;
            this.nextError = ne;
            return next();
        }
    }

    public static void main(String[] args) {
        testNextReturnsNextProvider();
        testNextThrowsServiceConfigurationError();
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsNextProvider() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class, null);
        // Create a dummy Provider instance
        ServiceLoader.Provider<String> dummyProvider = new ServiceLoader.Provider<String>() {
            public String get() { return "test"; }
            public String type() { return String.class; }
        };

        ServiceLoader.Provider<String> result = loader.nextWithState(dummyProvider, null);
        if (result != dummyProvider) {
            throw new AssertionError("next() did not return the expected provider");
        }
        if (loader.nextProvider != null) {
            throw new AssertionError("nextProvider was not cleared after next()");
        }
    }

    static void testNextThrowsServiceConfigurationError() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class, null);
        ServiceConfigurationError error = new ServiceConfigurationError("test error");

        try {
            loader.nextWithState(null, error);
            throw new AssertionError("next() did not throw ServiceConfigurationError when nextProvider is null and nextError is set");
        } catch (ServiceConfigurationError e) {
            if (e != error) {
                throw new AssertionError("next() threw a different ServiceConfigurationError instance");
            }
        }
    }

    static void testNextThrowsNoSuchElementException() {
        TestServiceLoader<String> loader = new TestServiceLoader<>(String.class, null);
        // Both nextProvider and nextError are null
        try {
            loader.nextWithState(null, null);
            throw new AssertionError("next() did not throw NoSuchElementException when hasNext() is false");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
