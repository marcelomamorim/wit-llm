/*
 * @test
 * @summary Test java.util.ServiceLoader.load(Class) basic behavior
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) {
        // Test loading a known service: Runnable (should not throw, may be empty)
        ServiceLoader<Runnable> loader = ServiceLoader.load(Runnable.class);
        if (loader == null) {
            throw new AssertionError("ServiceLoader.load returned null");
        }
        // The iterator() method should not throw and should be usable
        try {
            for (Runnable r : loader) {
                if (r == null) {
                    throw new AssertionError("ServiceLoader returned null provider");
                }
                // We do not assert on providers presence, just that iteration works
                break; // test at least one iteration
            }
        } catch (Throwable t) {
            throw new AssertionError("Iteration over ServiceLoader providers threw", t);
        }

        // Test loading with null argument throws NullPointerException
        try {
            ServiceLoader.load(null);
            throw new AssertionError("Expected NullPointerException for null service argument");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Expected NullPointerException but got " + t, t);
        }
    }
}
