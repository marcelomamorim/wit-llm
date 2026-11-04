/*
 * @test
 * @summary Witup-style tests for Module.addUses based on its observable source behavior
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testNullServiceThrowsNPE();
        testUnnamedOrOwnModuleReturnsThis();
        testNamedModuleCalledFromOtherCallerThrowsIllegalCaller();
    }

    static void testNullServiceThrowsNPE() {
        try {
            Object.class.getModule().addUses(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(service)
        }
    }

    static void testUnnamedOrOwnModuleReturnsThis() {
        Module m = ModuleWitupTest.class.getModule();
        Module result = m.addUses(String.class);
        if (result != m) {
            throw new RuntimeException("addUses must return this module");
        }
    }

    static void testNamedModuleCalledFromOtherCallerThrowsIllegalCaller() {
        try {
            Object.class.getModule().addUses(Runnable.class);
            throw new RuntimeException("expected IllegalCallerException");
        } catch (IllegalCallerException expected) {
            // expected: caller module differs from java.base
        }
    }
}
