/*
 * @test
 * @summary Tests for ResourceBundle.clearCache(ClassLoader)
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.Objects;
import java.net.URL;
import java.net.URLClassLoader;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testClearCacheWithNullLoader();
        testClearCacheWithValidLoader();
    }

    public static void testClearCacheWithNullLoader() {
        try {
            ResourceBundle.clearCache(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testClearCacheWithValidLoader() {
        ClassLoader loader = new URLClassLoader(new URL[0]);
        try {
            ResourceBundle.clearCache(loader);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
