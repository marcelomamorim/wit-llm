/*
 * @test
 * @summary Test the ServiceLoader.load method for a valid service class.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testLoadWithValidService();
    }

    public static void testLoadWithValidService() {
        try {
            // Attempt to load a service class that is expected to exist.
            // Using java.util.List as a generic service for testing.
            ServiceLoader<java.util.List> loader = ServiceLoader.load(java.util.List.class);
            boolean hasProviders = false;
            for (java.util.List provider : loader) {
                hasProviders = true;
                break;
            }
            // The test passes if no exception is thrown and the loader is iterable.
            // No specific provider is required to be present.
            if (loader == null) {
                throw new AssertionError("ServiceLoader.load returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during load: " + e, e);
        }
    }
}
