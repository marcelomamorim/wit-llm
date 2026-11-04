/*
 * @test
 * @summary Test ServiceLoader.loadInstalled method behavior
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) {
        // Use a known interface to test loadInstalled, e.g. Runnable
        ServiceLoader<Runnable> loader = ServiceLoader.loadInstalled(Runnable.class);
        if (loader == null) {
            throw new RuntimeException("ServiceLoader.loadInstalled returned null");
        }
        // The iterator() should not throw and should be non-null
        try {
            if (loader.iterator() == null) {
                throw new RuntimeException("ServiceLoader.iterator() returned null");
            }
        } catch (Throwable t) {
            throw new RuntimeException("ServiceLoader.iterator() threw " + t, t);
        }
        // The service() method should return the same Class object passed in
        if (loader.service() != Runnable.class) {
            throw new RuntimeException("ServiceLoader.service() does not match input class");
        }
    }
}
