/*
 * @test
 * @summary Test URLClassLoader.getResourceAsStream(String) for null argument and resource loading
 * @run main URLClassLoaderWitupTest
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.io.InputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.net.JarURLConnection;
import java.util.Objects;

public class URLClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNonExistentResourceReturnsNull();
        testExistingResourceReturnsInputStream();
    }

    private static void testNullNameThrowsNPE() {
        URLClassLoader cl = new URLClassLoader(new URL[0]);
        try {
            cl.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNonExistentResourceReturnsNull() {
        URLClassLoader cl = new URLClassLoader(new URL[0]);
        InputStream is = cl.getResourceAsStream("nonexistent_resource_12345");
        if (is != null) {
            throw new RuntimeException("Expected null InputStream for non-existent resource");
        }
    }

    private static void testExistingResourceReturnsInputStream() throws IOException {
        // Use a known resource from the system class loader or URLClassLoader
        ClassLoader system = ClassLoader.getSystemClassLoader();
        if (!(system instanceof URLClassLoader)) {
            // If system class loader is not URLClassLoader, skip this test
            return;
        }
        URLClassLoader cl = (URLClassLoader) system;

        // Try to load a known resource: java/lang/String.class
        InputStream is = cl.getResourceAsStream("java/lang/String.class");
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource java/lang/String.class");
        }
        // Close the stream
        is.close();
    }
}
