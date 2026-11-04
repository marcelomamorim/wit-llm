/*
 * @test
 * @summary Test java.lang.ClassLoader.getResource(String) method behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.util.Objects;

public class ClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testGetResourceReturnsNullForUnknownResource();
        testGetResourceReturnsNonNullForKnownResource();
    }

    static void testNullNameThrowsNPE() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getResource(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testGetResourceReturnsNullForUnknownResource() {
        ClassLoader cl = new ClassLoader() {
            @Override
            protected URL findResource(String name) {
                return null;
            }

            @Override
            public ClassLoader getParent() {
                return null;
            }
        };
        URL url = cl.getResource("nonexistent_resource_xyz");
        if (url != null) {
            throw new RuntimeException("Expected null URL for unknown resource");
        }
    }

    static void testGetResourceReturnsNonNullForKnownResource() {
        final URL expectedURL = ClassLoaderWitupTest.class.getResource("/java/lang/String.class");
        ClassLoader cl = new ClassLoader() {
            @Override
            protected URL findResource(String name) {
                if ("java/lang/String.class".equals(name)) {
                    return expectedURL;
                }
                return null;
            }

            @Override
            public ClassLoader getParent() {
                return null;
            }
        };
        URL url = cl.getResource("java/lang/String.class");
        if (!Objects.equals(url, expectedURL)) {
            throw new RuntimeException("Expected URL to match the known resource URL");
        }
    }
}
