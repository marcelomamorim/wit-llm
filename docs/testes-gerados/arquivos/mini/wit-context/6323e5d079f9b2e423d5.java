/*
 * @test
 * @summary Test ResourceBundle.clearCache(ClassLoader) for null argument and normal behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.Objects;
import java.lang.Module;
import java.lang.ClassLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testClearCacheNullLoader();
        testClearCacheWithDummyClassLoader();
    }

    private static void testClearCacheNullLoader() {
        try {
            ResourceBundle.clearCache(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testClearCacheWithDummyClassLoader() {
        // We cannot access ResourceBundle.cacheList directly (private), so we test that
        // calling clearCache with a dummy ClassLoader does not throw and completes.
        // This is a minimal observable test.

        ClassLoader dummyLoader = new ClassLoader() {};

        // Call clearCache with dummyLoader, expecting no exception
        ResourceBundle.clearCache(dummyLoader);

        // No direct observable effect, but no exception means pass
    }
}
