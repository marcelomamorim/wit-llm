/*
 * @test
 * @summary Test constructor of ModuleVisitor with valid and invalid api versions
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
        ModuleVisitor mv = new ModuleVisitor(Opcodes.ASM6, null) {
        };
        if (mv.api != Opcodes.ASM6) {
            throw new AssertionError("api field not set correctly");
        }
    }

    private static void testInvalidApi() {
        try {
            new ModuleVisitor(Opcodes.ASM6 - 1, null) {
            };
            throw new AssertionError("Expected IllegalArgumentException for invalid api");
        } catch (IllegalArgumentException expected) {
        }
    }
}
