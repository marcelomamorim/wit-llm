/*
 * @test
 * @summary Test java.lang.ClassLoader.getSystemResourceAsStream(String) behavior
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.IOException;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameReturnsNull();
        testNonExistentResourceReturnsNull();
        testExistingResourceReturnsStream();
    }

    private static void testNullNameReturnsNull() {
        InputStream is = ClassLoader.getSystemResourceAsStream(null);
        if (is != null) {
            throw new RuntimeException("Expected null InputStream for null resource name");
        }
    }

    private static void testNonExistentResourceReturnsNull() {
        InputStream is = ClassLoader.getSystemResourceAsStream("nonexistent_resource_1234567890.txt");
        if (is != null) {
            throw new RuntimeException("Expected null InputStream for non-existent resource");
        }
    }

    private static void testExistingResourceReturnsStream() throws IOException {
        // Use a known resource that should exist in the system class loader
        // For example, java/lang/String.class is always present
        String resourceName = "java/lang/String.class";
        InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource: " + resourceName);
        }
        // Read a few bytes to confirm stream is valid
        int firstByte = is.read();
        if (firstByte == -1) {
            throw new RuntimeException("InputStream for resource " + resourceName + " is empty");
        }
        is.close();
    }
}
