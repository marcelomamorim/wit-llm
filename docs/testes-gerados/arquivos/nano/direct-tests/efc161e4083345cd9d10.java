/*
 * @test
 * @summary Test Loader.getResource(String) method for various resource names.
 * @run main LoaderWitupTest
 */

import java.net.URL;
import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceExisting();
        testGetResourceNonExisting();
        testGetResourceNull();
    }

    static void testGetResourceExisting() {
        Loader loader = createLoader();
        String resourceName = "java/lang/Object.class";
        URL url = loader.getResource(resourceName);
        if (url == null) {
            throw new AssertionError("Expected resource to be found: " + resourceName);
        }
        System.out.println("Resource found: " + url);
    }

    static void testGetResourceNonExisting() {
        Loader loader = createLoader();
        String resourceName = "non/existent/resource.txt";
        URL url = loader.getResource(resourceName);
        if (url != null) {
            throw new AssertionError("Did not expect resource to be found: " + resourceName);
        }
        System.out.println("Resource not found as expected: " + resourceName);
    }

    static void testGetResourceNull() {
        Loader loader = createLoader();
        try {
            loader.getResource(null);
            throw new AssertionError("Expected NullPointerException for null resource name");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught as expected");
        }
    }

    static Loader createLoader() {
        // Use the bootstrap class loader as parent
        ClassLoader parentLoader = null;
        // Instantiate Loader with no modules and parent
        return new Loader(Collections.emptyList(), parentLoader);
    }
}
