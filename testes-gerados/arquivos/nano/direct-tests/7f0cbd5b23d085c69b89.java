/*
 * @test
 * @summary Test getResources method of ClassLoader for various resource names.
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.io.IOException;
import java.util.Enumeration;

public class ClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourcesWithNull();
        testGetResourcesWithNonExistentResource();
        testGetResourcesWithExistingResource();
    }

    static void testGetResourcesWithNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResources(null);
            System.out.println("FAIL: Expected NullPointerException for null resource name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testGetResourcesWithNonExistentResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            Enumeration<URL> resources = cl.getResources("nonexistent_resource_12345");
            if (resources.hasMoreElements()) {
                System.out.println("FAIL: Expected no resources, but found some");
            } else {
                System.out.println("PASS: No resources found as expected");
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown: " + e);
        }
    }

    static void testGetResourcesWithExistingResource() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            Enumeration<URL> resources = cl.getResources("java/lang/Object.class");
            boolean found = false;
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url != null) {
                    found = true;
                    System.out.println("Found resource URL: " + url);
                }
            }
            if (found) {
                System.out.println("PASS: Found existing resource(s)");
            } else {
                System.out.println("FAIL: Did not find existing resource");
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown: " + e);
        }
    }
}
