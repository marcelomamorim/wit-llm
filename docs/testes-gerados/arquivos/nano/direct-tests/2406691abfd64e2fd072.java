/*
 * @test
 * @summary Tests the getResource method of java.lang.ClassLoader.
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.lang.ClassLoader;
import java.util.Objects;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceWithNullName();
        testGetResourceWithExistingResource();
        testGetResourceWithNonExistingResource();
    }

    static void testGetResourceWithNullName() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResource(null);
            throw new AssertionError("Expected NullPointerException for null resource name");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testGetResourceWithExistingResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL url = cl.getResource("java/lang/Object.class");
        if (url == null) {
            throw new AssertionError("Resource 'java/lang/Object.class' should exist");
        }
        // Optionally, verify that URL points to a valid resource
        String urlStr = url.toString();
        if (!urlStr.contains("java/lang/Object.class")) {
            throw new AssertionError("URL does not contain expected resource path");
        }
    }

    static void testGetResourceWithNonExistingResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL url = cl.getResource("non/existent/resource.txt");
        if (url != null) {
            throw new AssertionError("Resource 'non/existent/resource.txt' should not exist");
        }
    }
}
