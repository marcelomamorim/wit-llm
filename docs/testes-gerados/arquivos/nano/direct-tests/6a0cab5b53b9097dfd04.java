/* @test    @summary Test getSystemResource method of ClassLoader for known resource names    @run main ClassLoaderWitupTest */

import java.net.URL;
import java.lang.ClassLoader;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetSystemResourceWithExistingResource();
        testGetSystemResourceWithNonExistingResource();
    }

    private static void testGetSystemResourceWithExistingResource() {
        String resourceName = "java/lang/Object.class";
        URL url = ClassLoader.getSystemResource(resourceName);
        if (url == null) {
            throw new AssertionError("Expected resource to be found: " + resourceName);
        }
        System.out.println("Resource found: " + url);
    }

    private static void testGetSystemResourceWithNonExistingResource() {
        String resourceName = "non/existent/resource.txt";
        URL url = ClassLoader.getSystemResource(resourceName);
        if (url != null) {
            throw new AssertionError("Did not expect resource to be found: " + resourceName);
        }
        System.out.println("Resource not found as expected: " + resourceName);
    }
}
