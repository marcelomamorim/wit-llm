/*
 * @test
 * @summary Behavioral tests for Module.isExported(String, Module)
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testExportedPackageToUnnamed() {
        Module base = String.class.getModule();
        Module unnamed = ModuleWitupTest.class.getModule();
        check(base.isExported("java.lang", unnamed),
              "java.lang should be exported from java.base to unnamed module");
    }

    static void testNonExportedInternalPackage() {
        Module base = Object.class.getModule();
        Module unnamed = ModuleWitupTest.class.getModule();
        check(!base.isExported("jdk.internal.misc", unnamed),
              "jdk.internal.misc should not be exported to unnamed module");
    }

    static void testNonexistentPackage() {
        Module base = Object.class.getModule();
        Module unnamed = ModuleWitupTest.class.getModule();
        check(!base.isExported("no.such.package.witup", unnamed),
              "nonexistent package should not be reported as exported");
    }

    static void testUnnamedModuleExportsEverything() {
        Module unnamed = ModuleWitupTest.class.getModule();
        Module base = String.class.getModule();
        check(unnamed.isExported("any.package.witup", base),
              "unnamed module should report all packages as exported");
    }

    static void testNullPackageThrowsNPE() {
        Module base = String.class.getModule();
        Module unnamed = ModuleWitupTest.class.getModule();
        try {
            base.isExported(null, unnamed);
            throw new RuntimeException("expected NullPointerException for null package name");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullOtherThrowsNPE() {
        Module base = String.class.getModule();
        try {
            base.isExported("java.lang", null);
            throw new RuntimeException("expected NullPointerException for null other module");
        } catch (NullPointerException expected) {
        }
    }

    public static void main(String[] args) {
        testExportedPackageToUnnamed();
        testNonExportedInternalPackage();
        testNonexistentPackage();
        testUnnamedModuleExportsEverything();
        testNullPackageThrowsNPE();
        testNullOtherThrowsNPE();
    }
}
