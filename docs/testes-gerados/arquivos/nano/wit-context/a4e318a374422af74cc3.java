/*
 * @test
 * @summary Test for ASMifier.visitInsn method to observe behavior with various opcodes.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitInsnWithValidOpcode();
        testVisitInsnWithInvalidOpcode();
    }

    private static void testVisitInsnWithValidOpcode() {
        ASMifier asmifier = new ASMifier();
        try {
            // Using a valid opcode, e.g., Opcodes.NOP (0)
            asmifier.visitInsn(0);
            System.out.println("visitInsn with valid opcode succeeded.");
        } catch (Exception e) {
            System.err.println("Unexpected exception with valid opcode: " + e);
        }
    }

    private static void testVisitInsnWithInvalidOpcode() {
        ASMifier asmifier = new ASMifier();
        int invalidOpcode = -1; // Invalid opcode
        try {
            asmifier.visitInsn(invalidOpcode);
            System.out.println("visitInsn with invalid opcode did not throw.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Expected ArrayIndexOutOfBoundsException for invalid opcode.");
        } catch (Exception e) {
            System.err.println("Unexpected exception with invalid opcode: " + e);
        }
    }
}
