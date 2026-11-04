/*
 * @test
 * @summary Test ServiceLoader.load(Class) for null argument and normal usage
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) {
        // Test 1: Passing null to load should throw NullPointerException
        try {
            ServiceLoader.load(null);
            throw new Error("Expected NullPointerException for null service class");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: Passing a valid service class should return a non-null ServiceLoader
        ServiceLoader<Runnable> loader = ServiceLoader.load(Runnable.class);
        if (loader == null) {
            throw new Error("ServiceLoader.load returned null for valid service class");
        }

        // Test 3: The returned ServiceLoader should be iterable without throwing
        try {
            for (Runnable r : loader) {
                // No action needed, just ensure no exception
                if (r == null) {
                    throw new Error("ServiceLoader iterator returned null element");
                }
            }
        } catch (Exception e) {
            throw new Error("Unexpected exception iterating ServiceLoader: " + e);
        }
    }
}
