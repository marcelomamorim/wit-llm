/*
 * @test
 * @summary Test getDefinedPackage method of ClassLoader for various package names.
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.lang.Package;
import java.util.Objects;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetDefinedPackageWithExistingPackage();
        testGetDefinedPackageWithNull();
        testGetDefinedPackageWithNonExistingPackage();
    }

    static void testGetDefinedPackageWithExistingPackage() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String packageName = "java.lang";
        Package pkg = cl.getDefinedPackage(packageName);
        if (pkg == null) {
            throw new AssertionError("Expected non-null package for 'java.lang'");
        }
        if (!Objects.equals(pkg.getName(), packageName)) {
            throw new AssertionError("Package name mismatch: expected 'java.lang', got '" + pkg.getName() + "'");
        }
        System.out.println("testGetDefinedPackageWithExistingPackage passed.");
    }

    static void testGetDefinedPackageWithNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getDefinedPackage(null);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException e) {
            System.out.println("testGetDefinedPackageWithNull passed.");
        }
    }

    static void testGetDefinedPackageWithNonExistingPackage() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String packageName = "com.nonexistent.package.test";
        Package pkg = cl.getDefinedPackage(packageName);
        if (pkg != null) {
            throw new AssertionError("Expected null for non-existing package '" + packageName + "'");
        }
        System.out.println("testGetDefinedPackageWithNonExistingPackage passed.");
    }
}
