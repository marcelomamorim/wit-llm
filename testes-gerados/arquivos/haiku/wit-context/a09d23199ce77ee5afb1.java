/*
 * @test
 * @summary Witup generated checks for ModuleLayer.findModule(String) null handling and lookup results
 * @run main ModuleLayerWitupTest
 */

import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testBootLayerFindsJavaBase();
        testEmptyLayerReturnsEmpty();
        testUnknownNameReturnsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNPE() {
        boolean thrown = false;
        try {
            ModuleLayer.boot().findModule(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "findModule(null) should throw NullPointerException");
    }

    static void testBootLayerFindsJavaBase() {
        Optional<Module> m = ModuleLayer.boot().findModule("java.base");
        check(m.isPresent(), "boot layer should contain java.base");
        check(m.get().getName().equals("java.base"), "returned module should be named java.base");
    }

    static void testEmptyLayerReturnsEmpty() {
        Optional<Module> m = ModuleLayer.empty().findModule("java.base");
        check(!m.isPresent(), "empty layer should not find java.base");
    }

    static void testUnknownNameReturnsEmpty() {
        Optional<Module> m = ModuleLayer.boot().findModule("no.such.module.witup");
        check(!m.isPresent(), "unknown module name should yield Optional.empty()");
    }
}
