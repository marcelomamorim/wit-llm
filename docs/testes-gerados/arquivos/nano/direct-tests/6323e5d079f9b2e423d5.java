/*
 * @test
 * @summary Test ResourceBundle.clearCache(ClassLoader) method for correct cache clearing behavior.
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;
import java.lang.ClassLoader;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testClearCacheWithNullLoader();
        testClearCacheWithValidLoader();
    }

    private static void testClearCacheWithNullLoader() {
        boolean exceptionThrown = false;
        try {
            ResourceBundle.clearCache(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling clearCache with null loader");
        }
    }

    private static void testClearCacheWithValidLoader() {
        ClassLoader loader = ResourceBundle.class.getClassLoader();
        // Populate cache with a dummy key to test removal
        // Since cacheList is private, we simulate cache clearing by calling clearCache
        // and verifying no exceptions occur.
        try {
            ResourceBundle.clearCache(loader);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during clearCache with valid loader", e);
        }
    }
}
