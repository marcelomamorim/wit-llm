/*
 * @test
 * @summary Verify Module.addUses null handling, unnamed module behavior and caller check on java.base
 * @run main ModuleWitupTest
 */
public class ModuleWitupTest {
    public static void main(String[] args) throws Exception {
        testNullServiceThrowsNPE();
        testUnnamedModuleReturnsSelf();
        testNamedJavaBaseCallerCheck();
    }

    static void testNullServiceThrowsNPE() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addUses(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testUnnamedModuleReturnsSelf() {
        Module m = ModuleWitupTest.class.getModule();
        if (!m.isNamed()) {
            Module r = m.addUses(String.class);
            if (r != m) {
                throw new RuntimeException("addUses must return this module");
            }
        }
    }

    static void testNamedJavaBaseCallerCheck() {
        Module base = Object.class.getModule();
        try {
            base.addUses(String.class);
            throw new RuntimeException("expected IllegalCallerException");
        } catch (IllegalCallerException e) {
            // expected: caller module is not java.base
        }
    }
}
