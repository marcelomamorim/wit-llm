/*
 * @test
 * @summary Test visitTypeInsn method of ASMifier for basic functionality and robustness
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with valid opcode and type
        asmifier.visitTypeInsn(0, "java/lang/String");
        asmifier.visitTypeInsn(1, "java/lang/Object");

        // Test with opcode at boundary (lowest valid opcode)
        asmifier.visitTypeInsn(0, "LMyType;");

        // Test with opcode at boundary (highest valid opcode in OPCODES array length assumed)
        // Since OPCODES is internal, we test with a high value that should not throw but produce output
        asmifier.visitTypeInsn(asmifier.OPCODES.length - 1, "LAnotherType;");

        // Test with null type - should not throw but appendConstant might handle null gracefully
        asmifier.visitTypeInsn(5, null);

        // Test with empty string type
        asmifier.visitTypeInsn(5, "");

        // No exceptions expected, just ensure no runtime exceptions occur
        System.out.println("ASMifier.visitTypeInsn tests passed without exceptions.");
    }
}
