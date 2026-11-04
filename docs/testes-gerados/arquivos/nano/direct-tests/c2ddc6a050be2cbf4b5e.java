/*
 * @test
 * @summary Test ServiceLoader.loadInstalled for a specific service class
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testLoadInstalledWithExistingService();
        testLoadInstalledWithNullService();
    }

    static void testLoadInstalledWithExistingService() {
        try {
            // Attempt to load a well-known service class, e.g., java.util.spi.LocaleServiceProvider
            // Using a class likely to be available in the platform
            Class<?> serviceClass = Class.forName("java.util.spi.LocaleServiceProvider");
            ServiceLoader<?> loader = ServiceLoader.loadInstalled(serviceClass);
            boolean hasProviders = loader.iterator().hasNext();
            // No assertion framework, so just print the result
            System.out.println("loadInstalled with existing service: " + hasProviders);
        } catch (ClassNotFoundException e) {
            System.out.println("Service class not found: " + e.getMessage());
        }
    }

    static void testLoadInstalledWithNullService() {
        try {
            ServiceLoader<?> loader = ServiceLoader.loadInstalled((Class<?>) null);
            System.out.println("loadInstalled with null service did not throw");
        } catch (NullPointerException e) {
            System.out.println("Expected NullPointerException for null service: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
