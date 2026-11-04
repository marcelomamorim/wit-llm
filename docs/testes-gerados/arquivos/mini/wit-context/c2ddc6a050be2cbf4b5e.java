/*
 * @test
 * @summary Test ServiceLoader.loadInstalled method for basic functionality and null handling
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) {
        testLoadInstalledWithValidService();
        testLoadInstalledWithNullService();
    }

    private static void testLoadInstalledWithValidService() {
        // Use a well-known interface from java.util as a service class for testing
        Class<Runnable> service = Runnable.class;
        ServiceLoader<Runnable> loader = ServiceLoader.loadInstalled(service);
        if (loader == null) {
            throw new AssertionError("ServiceLoader.loadInstalled returned null for valid service class");
        }
        // The loader should be for the requested service class
        if (!loader.getClass().getName().contains("ServiceLoader")) {
            throw new AssertionError("Returned object is not a ServiceLoader instance");
        }
    }

    private static void testLoadInstalledWithNullService() {
        try {
            ServiceLoader.loadInstalled(null);
            throw new AssertionError("Expected NullPointerException when service is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
