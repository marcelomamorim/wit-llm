/*
 * @test
 * @summary Observable behavior of URLClassLoader construction via public constructors that reach the package-private URLClassLoader(URL[], AccessControlContext) path
 * @run main URLClassLoaderWitupTest
 */

import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyUrlsWithNullParentConstructsLoader();
        testMissingClassThrowsClassNotFoundException();
        testMissingResourceReturnsNull();
        testNonexistentDirectoryUrlDoesNotFailConstruction();
        testStaticFactoryWithEmptyUrls();
    }

    static void testEmptyUrlsWithNullParentConstructsLoader() throws Exception {
        try (URLClassLoader loader = new URLClassLoader(new URL[0], null)) {
            if (loader == null) {
                throw new RuntimeException("loader must not be null");
            }
        }
    }

    static void testMissingClassThrowsClassNotFoundException() throws Exception {
        try (URLClassLoader loader = new URLClassLoader(new URL[0], null)) {
            try {
                loader.loadClass("witup.generated.DoesNotExist");
                throw new RuntimeException("expected ClassNotFoundException");
            } catch (ClassNotFoundException expected) {
                // expected
            }
        }
    }

    static void testMissingResourceReturnsNull() throws Exception {
        try (URLClassLoader loader = new URLClassLoader(new URL[0], null)) {
            if (loader.getResource("witup/generated/missing-resource.txt") != null) {
                throw new RuntimeException("missing resource must return null");
            }
        }
    }

    static void testNonexistentDirectoryUrlDoesNotFailConstruction() throws Exception {
        URL dir = new URL("file:/witup-nonexistent-dir-7f3a/");
        try (URLClassLoader loader = new URLClassLoader(new URL[] { dir }, null)) {
            try {
                loader.loadClass("witup.generated.Missing");
                throw new RuntimeException("expected ClassNotFoundException");
            } catch (ClassNotFoundException expected) {
                // expected
            }
        }
    }

    static void testStaticFactoryWithEmptyUrls() throws Exception {
        try (URLClassLoader loader = URLClassLoader.newInstance(new URL[0])) {
            if (loader == null) {
                throw new RuntimeException("factory must not return null");
            }
        }
    }
}
