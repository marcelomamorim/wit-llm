/*
 * @test
 * @summary Test java.lang.ClassLoader.getSystemResource(String) method
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.lang.ClassLoader;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with a known resource that should exist in the system class loader
        // Using "java/lang/String.class" as a resource that should always be found
        URL url = ClassLoader.getSystemResource("java/lang/String.class");
        if (url == null) {
            throw new RuntimeException("Expected resource java/lang/String.class not found");
        }

        // Test with a resource that does not exist, expecting null
        URL missing = ClassLoader.getSystemResource("nonexistent/resource/xyz123.txt");
        if (missing != null) {
            throw new RuntimeException("Expected null for nonexistent resource but got " + missing);
        }

        // Test with empty string resource name, expecting null
        URL empty = ClassLoader.getSystemResource("");
        if (empty != null) {
            throw new RuntimeException("Expected null for empty resource name but got " + empty);
        }

        // Test with null argument - should throw NullPointerException
        try {
            ClassLoader.getSystemResource(null);
            throw new RuntimeException("Expected NullPointerException for null resource name");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
