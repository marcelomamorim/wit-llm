/*
 * @test
 * @summary Test for ServiceLoader.loadInstalled(Class) method behavior
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testLoadInstalledWithNullService();
        testLoadInstalledWithValidService();
    }

    private static void testLoadInstalledWithNullService() {
        try {
            ServiceLoader.loadInstalled(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null service");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testLoadInstalledWithValidService() {
        // Using a known service class, e.g., java.util.spi.LocaleServiceProvider
        // which is a standard service in Java 11+28
        try {
            ServiceLoader.loadInstalled(java.util.spi.LocaleServiceProvider.class);
            System.out.println("ServiceLoader.loadInstalled executed successfully with valid service");
        } catch (Throwable t) {
            System.err.println("Unexpected exception during valid service load: " + t);
            System.exit(1);
        }
    }
}
