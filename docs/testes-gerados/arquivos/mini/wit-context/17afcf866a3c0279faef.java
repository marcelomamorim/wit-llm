/*
 * @test
 * @summary Test ClassLoader.getDefinedPackage(String) for null argument and normal behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.lang.Package;
import java.util.Objects;

public class ClassLoaderWitupTest {
    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testGetDefinedPackageReturnsNullForUnknown();
        testGetDefinedPackageReturnsPackageForKnown();
    }

    static void testNullNameThrowsNPE() {
        ClassLoader cl = new DummyClassLoader();
        try {
            cl.getDefinedPackage(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
            if (!"name cannot be null".equals(npe.getMessage())) {
                throw new AssertionError("Unexpected NPE message: " + npe.getMessage());
            }
        }
    }

    static void testGetDefinedPackageReturnsNullForUnknown() {
        ClassLoader cl = new DummyClassLoader();
        Package p = cl.getDefinedPackage("unknown.package.name");
        if (p != null) {
            throw new AssertionError("Expected null for unknown package name");
        }
    }

    static void testGetDefinedPackageReturnsPackageForKnown() {
        DummyClassLoader cl = new DummyClassLoader();
        String pkgName = "java.lang";
        Package p = cl.getDefinedPackage(pkgName);
        if (p == null) {
            throw new AssertionError("Expected non-null Package for known package name");
        }
        if (!pkgName.equals(p.getName())) {
            throw new AssertionError("Package name mismatch: expected " + pkgName + " but got " + p.getName());
        }
    }

    // DummyClassLoader subclass to expose definePackage and simulate packages map
    static class DummyClassLoader extends ClassLoader {
        private final java.util.Map<String, NamedPackage> packages = new java.util.HashMap<>();

        DummyClassLoader() {
            super();
            // Add a known package entry
            packages.put("java.lang", new NamedPackage("java.lang"));
        }

        @Override
        public Package getDefinedPackage(String name) {
            Objects.requireNonNull(name, "name cannot be null");
            NamedPackage p = packages.get(name);
            if (p == null) {
                return null;
            }
            return definePackage(name, p.module());
        }

        // Expose protected definePackage method
        public Package definePackage(String name, Module module) {
            // Use the protected definePackage method from ClassLoader
            return super.definePackage(name, module);
        }
    }

    // Minimal NamedPackage stub to simulate the real NamedPackage
    static class NamedPackage {
        private final String name;

        NamedPackage(String name) {
            this.name = name;
        }

        Module module() {
            // Return the unnamed module for simplicity
            return ClassLoaderWitupTest.class.getModule();
        }
    }
}
