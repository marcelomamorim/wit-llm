/*
 * @test
 * @summary Test jdk.internal.loader.Loader.getResource(String) method
 * @run main LoaderWitupTest
 */

import java.net.URL;
import java.util.Objects;
import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceWithNullName();
        testGetResourceWithNonExistingResource();
        testGetResourceWithExistingResource();
    }

    private static void testGetResourceWithNullName() {
        Loader loader = new Loader(new java.util.ArrayList<>(), null);
        try {
            loader.getResource(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetResourceWithNonExistingResource() {
        Loader loader = new Loader(new java.util.ArrayList<>(), null);
        // The resource "nonexistent_resource_12345" should not exist
        URL url = loader.getResource("nonexistent_resource_12345");
        if (url != null) {
            throw new RuntimeException("Expected null URL for non-existing resource");
        }
    }

    private static void testGetResourceWithExistingResource() throws Exception {
        // Use a resource that should exist in the system class loader or boot loader
        Loader loader = new Loader(new java.util.ArrayList<>(), ClassLoader.getSystemClassLoader());
        // For example, "java/lang/String.class" is a resource that should be found
        String resourceName = "java/lang/String.class";
        URL url = loader.getResource(resourceName);
        if (url == null) {
            throw new RuntimeException("Expected non-null URL for existing resource: " + resourceName);
        }
        // The URL should end with the resource name
        if (!url.toString().endsWith(resourceName)) {
            throw new RuntimeException("URL does not end with resource name: " + url);
        }
    }
}
