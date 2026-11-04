/*
 * @test
 * @summary Test ClassLoader.getDefinedPackage(String) behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.lang.Package;
import java.util.Objects;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetDefinedPackageWithNullName();
        testGetDefinedPackageWithUnknownName();
        testGetDefinedPackageWithKnownName();
    }

    static void testGetDefinedPackageWithNullName() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getDefinedPackage(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testGetDefinedPackageWithUnknownName() {
        ClassLoader cl = new ClassLoader() {};
        Package p = cl.getDefinedPackage("non.existent.package.name");
        if (p != null) {
            throw new RuntimeException("Expected null for unknown package name");
        }
    }

    static void testGetDefinedPackageWithKnownName() throws Exception {
        // We create a subclass of ClassLoader that defines a package manually
        final String pkgName = "java.lang";
        ClassLoader cl = new ClassLoader() {
            {
                // Use definePackage to define a package with the given name
                definePackage(pkgName, "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", null, null);
            }

            // Override getDefinedPackage to call super method
            @Override
            public Package getDefinedPackage(String name) {
                return super.getDefinedPackage(name);
            }
        };

        Package p = cl.getDefinedPackage(pkgName);
        if (p == null) {
            throw new RuntimeException("Expected non-null Package for known package name");
        }
        if (!pkgName.equals(p.getName())) {
            throw new RuntimeException("Package name mismatch: expected " + pkgName + " but got " + p.getName());
        }
    }
}
