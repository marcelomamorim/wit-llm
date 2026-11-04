/*
 * @test
 * @summary Test java.net.URLClassLoader.getResourceAsStream(String) behavior
 * @run main URLClassLoaderWitupTest
 */

import java.net.URLClassLoader;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.util.Objects;

public class URLClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testGetResourceAsStreamReturnsNullForNonexistentResource();
        testGetResourceAsStreamReturnsStreamForExistingResource();
    }

    private static void testNullNameThrowsNPE() {
        URLClassLoader cl = new URLClassLoader(new URL[0]);
        try {
            cl.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetResourceAsStreamReturnsNullForNonexistentResource() {
        URLClassLoader cl = new URLClassLoader(new URL[0]);
        InputStream is = cl.getResourceAsStream("nonexistent_resource_12345.txt");
        if (is != null) {
            try {
                is.close();
            } catch (IOException ignored) {}
            throw new RuntimeException("Expected null InputStream for nonexistent resource");
        }
    }

    private static void testGetResourceAsStreamReturnsStreamForExistingResource() throws IOException {
        // Use a known resource that should exist in the runtime: java/lang/String.class
        URLClassLoader cl = new URLClassLoader(new URL[0]);
        InputStream is = cl.getResourceAsStream("java/lang/String.class");
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource java/lang/String.class");
        }
        // Read a few bytes to confirm stream is valid
        byte[] buffer = new byte[4];
        int read = is.read(buffer);
        if (read <= 0) {
            throw new RuntimeException("Expected to read bytes from InputStream");
        }
        is.close();
    }
}
