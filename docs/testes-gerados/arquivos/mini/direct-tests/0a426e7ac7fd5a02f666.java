/*
 * @test
 * @summary Test ModuleVisitor constructor with api parameter
 * @run main ModuleVisitorWitupTest
 */

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        // Test constructor with valid api value
        int api = 123;
        ModuleVisitor mv = new ModuleVisitor(api) {
            // No override needed for this test
        };
        if (mv.api != api) {
            throw new AssertionError("api field not set correctly");
        }

        // Test constructor with api and null ModuleVisitor
        ModuleVisitor mv2 = new ModuleVisitor(api, null) {
            // No override needed for this test
        };
        if (mv2.api != api) {
            throw new AssertionError("api field not set correctly in two-arg constructor");
        }
    }
}

abstract class ModuleVisitor {
    protected final int api;
    protected final ModuleVisitor mv;

    public ModuleVisitor(final int api) {
        this(api, null);
    }

    public ModuleVisitor(final int api, final ModuleVisitor mv) {
        this.api = api;
        this.mv = mv;
    }
}
