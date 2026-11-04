/*
 * @test
 * @summary Test getResourceAsStream method of ClassLoader for various resource names.
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceAsStreamWithExistingResource();
        testGetResourceAsStreamWithNonExistingResource();
        testGetResourceAsStreamWithNull();
    }

    static void testGetResourceAsStreamWithExistingResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String resourceName = "java/lang/Object.class";
        InputStream is = cl.getResourceAsStream(resourceName);
        if (is == null) {
            throw new AssertionError("Expected resource to be found: " + resourceName);
        }
        try {
            int available = is.available();
            if (available <= 0) {
                throw new AssertionError("InputStream should have available bytes for resource: " + resourceName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    static void testGetResourceAsStreamWithNonExistingResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String resourceName = "non/existent/resource.txt";
        InputStream is = cl.getResourceAsStream(resourceName);
        if (is != null) {
            throw new AssertionError("Expected null for non-existent resource: " + resourceName);
        }
    }

    static void testGetResourceAsStreamWithNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResourceAsStream(null);
            throw new AssertionError("Expected NullPointerException for null resource name");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
