/*
 * @test
 * @summary Verify Loader.getResource null handling, missing resource lookup and parent delegation
 * @modules java.base/jdk.internal.loader
 * @run main LoaderWitupTest
 */

import java.lang.module.ResolvedModule;
import java.net.URL;
import java.util.Collections;

import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testMissingResourceWithNullParentReturnsNull();
        testParentDelegationReturnsParentUrl();
        testParentDelegationMissingReturnsNull();
    }

    static void testNullNameThrowsNPE() {
        Loader loader = new Loader(Collections.<ResolvedModule>emptyList(), null);
        boolean thrown = false;
        try {
            loader.getResource(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null name");
        }
    }

    static void testMissingResourceWithNullParentReturnsNull() {
        Loader loader = new Loader(Collections.<ResolvedModule>emptyList(), null);
        URL url = loader.getResource("no/such/resource-witup-xyz.txt");
        if (url != null) {
            throw new RuntimeException("expected null for missing resource, got " + url);
        }
    }

    static void testParentDelegationReturnsParentUrl() throws Exception {
        final URL marker = new URL("http://example.invalid/x.txt");
        ClassLoader parent = new ClassLoader(null) {
            @Override
            public URL getResource(String name) {
                return "x.txt".equals(name) ? marker : null;
            }
        };
        Loader loader = new Loader(Collections.<ResolvedModule>emptyList(), parent);
        URL url = loader.getResource("x.txt");
        if (url != marker) {
            throw new RuntimeException("expected parent URL, got " + url);
        }
    }

    static void testParentDelegationMissingReturnsNull() throws Exception {
        final URL marker = new URL("http://example.invalid/x.txt");
        ClassLoader parent = new ClassLoader(null) {
            @Override
            public URL getResource(String name) {
                return "x.txt".equals(name) ? marker : null;
            }
        };
        Loader loader = new Loader(Collections.<ResolvedModule>emptyList(), parent);
        URL url = loader.getResource("y.txt");
        if (url != null) {
            throw new RuntimeException("expected null for unknown name, got " + url);
        }
    }
}
