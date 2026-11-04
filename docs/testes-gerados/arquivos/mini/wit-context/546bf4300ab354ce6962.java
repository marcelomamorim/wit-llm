/*
 * @test
 * @summary Test constructor of ModuleVisitor for API version validation and delegation
 * @run main ModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApi();
    }

    private static void testValidApi() {
        // Create a ModuleVisitor with valid API version Opcodes.ASM6
        ModuleVisitor mv = new ModuleVisitor(Opcodes.ASM6, null) {
            // No override needed for this test
        };
        if (mv == null) {
            throw new AssertionError("ModuleVisitor instance should not be null");
        }
    }

    private static void testInvalidApi() {
        // The constructor should throw IllegalArgumentException if api != Opcodes.ASM6
        boolean thrown = false;
        try {
            new ModuleVisitor(Opcodes.ASM6 - 1, null) {
                // No override needed
            };
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid api version");
        }
    }
}
