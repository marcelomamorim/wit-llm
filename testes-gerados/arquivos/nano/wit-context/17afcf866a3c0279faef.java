/*
 * @test
 * @summary Test for ClassLoader.getDefinedPackage(String)
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.lang.Package;
import java.util.HashMap;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNullPointerException();
        testExistingPackageReturnsPackage();
        testNonExistingPackageReturnsNull();
    }

    private static void testNullNameThrowsNullPointerException() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getDefinedPackage(null);
            System.err.println("Expected NullPointerException for null name");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testExistingPackageReturnsPackage() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String packageName = "java.lang";
        Package pkg = cl.getDefinedPackage(packageName);
        if (pkg == null) {
            System.err.println("Package 'java.lang' should be defined");
            System.exit(1);
        }
        if (!pkg.getName().equals(packageName)) {
            System.err.println("Returned package name mismatch");
            System.exit(1);
        }
    }

    private static void testNonExistingPackageReturnsNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String fakePackageName = "com.nonexistent.fakepackage";
        Package pkg = cl.getDefinedPackage(fakePackageName);
        if (pkg != null) {
            System.err.println("Expected null for non-existent package");
            System.exit(1);
        }
    }
}
