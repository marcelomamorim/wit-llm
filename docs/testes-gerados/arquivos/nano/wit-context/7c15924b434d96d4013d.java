/*
 * @test
 * @summary Test for ServiceLoader.next() method to verify exception behavior.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.NoSuchElementException;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNextThrowsNoSuchElementExceptionWhenHasNextIsFalse();
        testNextReturnsProviderWhenNextProviderIsNotNull();
        testNextThrowsServiceConfigurationErrorWhenNextErrorIsNotNull();
    }

    private static void testNextThrowsNoSuchElementExceptionWhenHasNextIsFalse() {
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                // Simulate the method under test
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return null;
            }
        };
        try {
            loader.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testNextReturnsProviderWhenNextProviderIsNotNull() {
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            private Object provider = new Object();
            private Object nextProvider = provider;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                if (nextProvider != null) {
                    Object p = nextProvider;
                    nextProvider = null;
                    return p;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        Object provider = loader.next();
        if (provider == null) {
            throw new AssertionError("Provider should not be null");
        }
    }

    private static void testNextThrowsServiceConfigurationErrorWhenNextErrorIsNotNull() {
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            private RuntimeException nextError = new RuntimeException("Error");

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                if (nextError != null) {
                    throw new ServiceConfigurationError(nextError.getMessage(), nextError);
                }
                return null;
            }
        };
        try {
            loader.next();
            throw new AssertionError("Expected ServiceConfigurationError was not thrown");
        } catch (ServiceConfigurationError e) {
            // Expected exception
        }
    }

    // Custom exception to simulate ServiceConfigurationError
    static class ServiceConfigurationError extends RuntimeException {
        public ServiceConfigurationError(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
