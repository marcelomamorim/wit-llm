/*
 * @test
 * @summary Test ResourceBundle.clearCache(ClassLoader) behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.Objects;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testClearCacheWithNullLoader();
        testClearCacheWithDummyLoader();
    }

    private static void testClearCacheWithNullLoader() {
        try {
            ResourceBundle.clearCache(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testClearCacheWithDummyLoader() {
        // Use the system class loader as a dummy loader
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        // Call clearCache with a valid loader, expect no exception
        ResourceBundle.clearCache(loader);
        // No direct observable effect, so just ensure no exception thrown
    }
}
