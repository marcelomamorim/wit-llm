/*
 * @test
 * @summary Verifies the public copy constructor Extension(Extension ext): null source raises NullPointerException and copying a default instance succeeds
 * @modules java.base/sun.security.x509
 * @run main ExtensionWitupTest
 */

import sun.security.x509.Extension;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSourceThrowsNPE();
        testCopyOfDefaultExtensionSucceeds();
        testCopyOfCopySucceeds();
    }

    static void testNullSourceThrowsNPE() {
        boolean thrown = false;
        try {
            new Extension((Extension) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null source");
        }
    }

    static void testCopyOfDefaultExtensionSucceeds() {
        Extension src = new Extension();
        Extension copy = new Extension(src);
        if (copy == null) {
            throw new RuntimeException("Copy constructor returned null");
        }
        if (copy == src) {
            throw new RuntimeException("Copy constructor must create a new instance");
        }
    }

    static void testCopyOfCopySucceeds() {
        Extension src = new Extension();
        Extension copy1 = new Extension(src);
        Extension copy2 = new Extension(copy1);
        if (copy2 == null) {
            throw new RuntimeException("Second-level copy returned null");
        }
    }
}
