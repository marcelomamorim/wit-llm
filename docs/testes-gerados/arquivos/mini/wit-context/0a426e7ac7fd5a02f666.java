/*
 * @test
 * @summary Test ModuleVisitor constructor with valid and invalid api versions
 * @run main ModuleVisitorWitupTest
 */

public class ModuleVisitorWitupTest {

    // Minimal subclass to allow instantiation of abstract ModuleVisitor
    static class TestModuleVisitor extends jdk.internal.org.objectweb.asm.ModuleVisitor {
        public TestModuleVisitor(int api) {
            super(api);
        }
        public TestModuleVisitor(int api, jdk.internal.org.objectweb.asm.ModuleVisitor mv) {
            super(api, mv);
        }
    }

    public static void main(String[] args) {
        // Valid api value: Opcodes.ASM6 == 6
        final int ASM6 = 6;

        // Test constructor with valid api
        TestModuleVisitor mv = new TestModuleVisitor(ASM6);
        if (mv == null) {
            throw new AssertionError("Constructor returned null for valid api");
        }

        // Test constructor with invalid api (not ASM6) - expect IllegalArgumentException
        boolean threw = false;
        try {
            new TestModuleVisitor(ASM6 + 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Constructor did not throw IllegalArgumentException for invalid api");
        }

        // Also test two-arg constructor with valid api and null visitor
        mv = new TestModuleVisitor(ASM6, null);
        if (mv == null) {
            throw new AssertionError("Two-arg constructor returned null for valid api and null visitor");
        }

        // Test two-arg constructor with invalid api and null visitor - expect IllegalArgumentException
        threw = false;
        try {
            new TestModuleVisitor(ASM6 - 1, null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Two-arg constructor did not throw IllegalArgumentException for invalid api");
        }
    }
}
