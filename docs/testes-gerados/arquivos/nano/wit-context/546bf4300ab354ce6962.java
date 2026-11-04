/* @test    @summary Test for ModuleVisitor constructor with valid and invalid api values    @run main ModuleVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApi();
    }

    private static void testValidApi() {
        ModuleVisitor mv = new ModuleVisitor(Opcodes.ASM6, null);
        // No exception expected for valid api
    }

    private static void testInvalidApi() {
        try {
            new ModuleVisitor(999, null);
            // Fail if no exception thrown
            throw new AssertionError("Expected IllegalArgumentException for invalid api");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
