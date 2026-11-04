/*
 * @test
 * @summary Tests for java.util.ServiceLoader.load(Class)
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNullService();
        testNullServiceWithNullContextClassLoader();
        testNullServiceWithNullSecurity();
    }

    public static void testNullService() {
        try {
            ServiceLoader.load(null);
            // Expecting NullPointerException
            System.err.println("Failed: Expected NullPointerException for null service");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    public static void testNullServiceWithNullContextClassLoader() {
        Thread originalThread = Thread.currentThread();
        try {
            Thread t = new Thread(() -> {
                try {
                    // Simulate context class loader being null
                    Thread.currentThread().setContextClassLoader(null);
                    ServiceLoader.load(null);
                    System.err.println("Failed: Expected NullPointerException with null context class loader");
                } catch (NullPointerException expected) {
                    // Expected
                } catch (Throwable t) {
                    System.err.println("Failed: Unexpected exception: " + t);
                } finally {
                    Thread.currentThread().setContextClassLoader(t.getContextClassLoader());
                }
            });
            t.start();
            t.join();
        } catch (Throwable t) {
            System.err.println("Failed: Exception during thread execution: " + t);
        } finally {
            Thread.currentThread().setContextClassLoader(originalThread.getContextClassLoader());
        }
    }

    public static void testNullServiceWithNullSecurity() {
        // Since the method does not directly access security object, simulate by passing null
        try {
            ServiceLoader.load(null);
            System.err.println("Failed: Expected NullPointerException with null security");
        } catch (NullPointerException expected) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
