/*
 * @test
 * @summary Test java.lang.ClassLoader.getResources(String) for null argument and normal behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.util.Enumeration;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws IOException {
        testGetResourcesNullName();
        testGetResourcesNormal();
    }

    private static void testGetResourcesNullName() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getResources(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }

    private static void testGetResourcesNormal() throws IOException {
        // Use system class loader as a concrete ClassLoader instance
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String resourceName = "java/lang/String.class";
        Enumeration<URL> resources = cl.getResources(resourceName);
        Objects.requireNonNull(resources);

        // Check that enumeration returns at least one element and does not throw
        boolean found = false;
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (url != null) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new AssertionError("Expected at least one resource for " + resourceName);
        }

        // Check that enumeration behaves correctly when exhausted
        try {
            while (true) {
                resources.nextElement();
            }
        } catch (NoSuchElementException expected) {
            // expected when enumeration exhausted
        }
    }
}
