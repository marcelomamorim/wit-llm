/*
 * @test
 * @summary Test System.definePackage method behavior
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.lang.Package;

public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        // Use the bootstrap class loader (null) which does not support definePackage
        ClassLoader bootstrapLoader = null;
        String pkgName = "java.lang";
        Module module = System.class.getModule();

        // When ClassLoader is null, definePackage should throw NullPointerException
        // because we call cl.definePackage and cl is null
        boolean npeThrown = false;
        try {
            System.definePackage(bootstrapLoader, pkgName, module);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when ClassLoader is null");
        }

        // Use the current class loader which should support definePackage
        ClassLoader cl = SystemWitupTest.class.getClassLoader();
        Package p = System.definePackage(cl, pkgName, module);
        if (p == null) {
            throw new RuntimeException("definePackage returned null for valid ClassLoader");
        }
        if (!pkgName.equals(p.getName())) {
            throw new RuntimeException("Package name mismatch: expected " + pkgName + " but got " + p.getName());
        }

        // The module of the returned package should be the same as passed
        if (p.getClass().getModule() != module) {
            throw new RuntimeException("Package module mismatch");
        }

        System.out.println("Test passed.");
    }
}
