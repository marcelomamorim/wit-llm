/*
 * @test
 * @summary Verifies Module.isOpen(String, Module) null-argument checks and the open-to-all behavior of unnamed modules
 * @run main ModuleWitupTest
 */

import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPackageNameThrowsNPE();
        testNullOtherModuleThrowsNPE();
        testBothNullThrowsNPE();
        testUnnamedModuleOpensAnyPackage();
    }

    static void testNullPackageNameThrowsNPE() {
        Module base = Object.class.getModule();
        try {
            base.isOpen(null, base);
            throw new AssertionError("Expected NullPointerException for null package name");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullOtherModuleThrowsNPE() {
        Module base = Object.class.getModule();
        try {
            base.isOpen("java.lang", null);
            throw new AssertionError("Expected NullPointerException for null other module");
        } catch (NullPointerException expected) {
        }
    }

    static void testBothNullThrowsNPE() {
        Module base = Object.class.getModule();
        try {
            base.isOpen(null, null);
            throw new AssertionError("Expected NullPointerException when both arguments are null");
        } catch (NullPointerException expected) {
        }
    }

    static void testUnnamedModuleOpensAnyPackage() {
        Module unnamed = ModuleWitupTest.class.getClassLoader().getUnnamedModule();
        Module base = Object.class.getModule();
        if (!unnamed.isOpen("java.lang", base)) {
            throw new AssertionError("Unnamed module should be open for any package");
        }
        if (!unnamed.isOpen("no.such.pkg", base)) {
            throw new AssertionError("Unnamed module should be open for any package name");
        }
    }
}
