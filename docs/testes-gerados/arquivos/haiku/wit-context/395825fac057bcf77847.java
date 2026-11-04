/*
 * @test
 * @summary Observable behavior of SecureClassLoader constructors and parent delegation
 * @run main SecureClassLoaderWitupTest
 */

import java.security.SecureClassLoader;

public class SecureClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testExplicitParentIsRetained();
        testNullParentIsAccepted();
        testDefaultConstructorUsesSystemClassLoaderAsParent();
    }

    static void testExplicitParentIsRetained() {
        ClassLoader parent = SecureClassLoaderWitupTest.class.getClassLoader();
        SecureClassLoader scl = new SecureClassLoader(parent) {};
        if (scl.getParent() != parent) {
            throw new RuntimeException("Explicit parent not retained");
        }
    }

    static void testNullParentIsAccepted() {
        SecureClassLoader scl = new SecureClassLoader((ClassLoader) null) {};
        if (scl.getParent() != null) {
            throw new RuntimeException("Null parent should be reported as null");
        }
    }

    static void testDefaultConstructorUsesSystemClassLoaderAsParent() {
        SecureClassLoader scl = new SecureClassLoader() {};
        if (scl.getParent() != ClassLoader.getSystemClassLoader()) {
            throw new RuntimeException("Default parent should be the system class loader");
        }
    }
}
