/*
 * @test
 * @summary Test for ClassLoader.getResources(String)
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.util.Enumeration;
import java.io.IOException;

public class ClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourcesWithNull();
        testGetResourcesWithValidName();
        testGetResourcesWithEmptyName();
    }

    static void testGetResourcesWithNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResources(null);
            System.err.println("Expected NullPointerException for null name");
            System.exit(1);
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    static void testGetResourcesWithValidName() throws IOException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Enumeration<URL> resources = cl.getResources("java/lang/Object.class");
        boolean found = false;
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (url != null) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.err.println("No resources found for 'java/lang/Object.class'");
            System.exit(1);
        }
    }

    static void testGetResourcesWithEmptyName() throws IOException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Enumeration<URL> resources = cl.getResources("");
        // Just ensure no exception thrown and enumeration is not null
        if (resources == null) {
            System.err.println("Resources enumeration is null for empty string");
            System.exit(1);
        }
        // Optionally, iterate to ensure no exception
        while (resources.hasMoreElements()) {
            resources.nextElement();
        }
    }
}
