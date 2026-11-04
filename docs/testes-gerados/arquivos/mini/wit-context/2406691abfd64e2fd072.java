/*
 * @test
 * @summary Test java.lang.ClassLoader.getResource(String) for null argument and normal behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.util.Objects;

public class ClassLoaderWitupTest {
    public static void main(String[] args) {
        testGetResourceNullName();
        testGetResourceNonNullName();
    }

    private static void testGetResourceNullName() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getResource(null);
            throw new Error("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetResourceNonNullName() {
        final String resourceName = "java/lang/String.class";
        ClassLoader cl = new ClassLoader() {
            @Override
            protected URL findResource(String name) {
                // Return null to simulate resource not found
                return null;
            }
        };

        // Since parent is null, BootLoader.findResource(name) is called internally,
        // but we cannot access BootLoader here, so we test that getResource returns null or non-null
        // depending on findResource and parent behavior.
        // We expect no exception and a URL or null result.

        URL url = cl.getResource(resourceName);
        // url may be null or non-null depending on BootLoader and parent, so just check no exception
        if (url != null && !resourceName.equals(url.getPath()) && !url.toString().contains(resourceName)) {
            throw new Error("Returned URL does not appear to match resource name");
        }
    }
}
