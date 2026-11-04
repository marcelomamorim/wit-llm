/*
 * @test
 * @summary Witup-generated checks for ResourceBundle.clearCache(ClassLoader) null guard and normal invocation
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testNullLoaderThrowsNPE();
        testSystemLoaderDoesNotThrow();
    }

    static void testNullLoaderThrowsNPE() {
        try {
            ResourceBundle.clearCache(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null loader");
    }

    static void testSystemLoaderDoesNotThrow() {
        ResourceBundle.clearCache(ClassLoader.getSystemClassLoader());
    }
}
