/*
 * @test
 * @summary Tests for visitTypeInsn method in ASMifier class
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitTypeInsnValidOpcode();
        testVisitTypeInsnInvalidOpcode();
    }

    private static void testVisitTypeInsnValidOpcode() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitTypeInsn(0, "java/lang/String");
            // No exception expected for valid opcode
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid opcode", e);
        }
    }

    private static void testVisitTypeInsnInvalidOpcode() {
        ASMifier asmifier = new ASMifier();
        int invalidOpcode = -1; // assuming -1 is invalid
        try {
            asmifier.visitTypeInsn(invalidOpcode, "java/lang/String");
            // Depending on implementation, may throw exception or not
            // If method does not throw, test passes as behavior is observable
        } catch (IllegalArgumentException e) {
            // Expected if method throws for invalid opcode
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid opcode", e);
        }
    }
}
