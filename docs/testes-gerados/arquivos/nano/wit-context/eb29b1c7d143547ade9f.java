/*
 * @test
 * @summary Test for URLClassLoader.getResourceAsStream(String)
 * @run main URLClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceAsStreamWithNullName();
        testGetResourceAsStreamWithNonExistentResource();
        testGetResourceAsStreamWithExistingResource();
    }

    private static void testGetResourceAsStreamWithNullName() {
        URLClassLoader loader = createSampleLoader();
        try {
            loader.getResourceAsStream(null);
            System.err.println("Expected NullPointerException for null resource name");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetResourceAsStreamWithNonExistentResource() {
        URLClassLoader loader = createSampleLoader();
        InputStream is = loader.getResourceAsStream("nonexistent_resource.txt");
        if (is != null) {
            System.err.println("Expected null for non-existent resource");
        }
    }

    private static void testGetResourceAsStreamWithExistingResource() {
        URLClassLoader loader = createSampleLoader();
        InputStream is = loader.getResourceAsStream("java/net/URLClassLoader.class");
        if (is == null) {
            System.err.println("Failed to load existing resource");
        } else {
            try {
                is.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private static URLClassLoader createSampleLoader() {
        URL[] urls = {};
        return new URLClassLoader(urls);
    }
}
