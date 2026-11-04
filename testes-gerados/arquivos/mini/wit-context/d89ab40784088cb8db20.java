/*
 * @test
 * @summary Test ClassLoader.getSystemResourceAsStream(String) for expected behavior
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.lang.ClassLoader;
import java.io.IOException;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameReturnsNull();
        testNonExistentResourceReturnsNull();
        testExistingResourceReturnsStream();
    }

    private static void testNullNameReturnsNull() {
        // getSystemResource(null) returns null, so getSystemResourceAsStream(null) should return null
        InputStream is = ClassLoader.getSystemResourceAsStream(null);
        if (is != null) {
            throw new RuntimeException("Expected null InputStream for null resource name");
        }
    }

    private static void testNonExistentResourceReturnsNull() {
        // A resource name that is very unlikely to exist
        String resourceName = "nonexistent_resource_1234567890.txt";
        InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // ignore
            }
            throw new RuntimeException("Expected null InputStream for non-existent resource");
        }
    }

    private static void testExistingResourceReturnsStream() throws IOException {
        // Use a resource that should exist in the runtime: java/lang/String.class
        String resourceName = "java/lang/String.class";
        InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource");
        }
        // Read at least one byte to confirm stream is valid
        int firstByte = is.read();
        if (firstByte == -1) {
            is.close();
            throw new RuntimeException("Expected non-empty InputStream for existing resource");
        }
        is.close();
    }
}
