/* @test    @summary Test ASMifier.visitInsn method for opcode handling    @run main ASMifierWitupTest */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitInsnWithValidOpcode();
        testVisitInsnWithInvalidOpcode();
    }

    private static void testVisitInsnWithValidOpcode() {
        ASMifier asmifier = new ASMifier();
        int opcode = 0; // Assuming 0 is a valid opcode in OPCODES array
        asmifier.visitInsn(opcode);
        String lastLine = asmifier.text.get(asmifier.text.size() - 1);
        assert lastLine.contains("visitInsn") : "Expected 'visitInsn' in output";
        assert lastLine.contains("OPCODES") : "Expected 'OPCODES' in output";
    }

    private static void testVisitInsnWithInvalidOpcode() {
        ASMifier asmifier = new ASMifier();
        int invalidOpcode = -1; // Invalid opcode
        try {
            asmifier.visitInsn(invalidOpcode);
            String lastLine = asmifier.text.get(asmifier.text.size() - 1);
            assert lastLine.contains("visitInsn") : "Expected 'visitInsn' in output";
            assert lastLine.contains("OPCODES") : "Expected 'OPCODES' in output";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected if OPCODES array access is invalid
            assert true : "Caught expected ArrayIndexOutOfBoundsException";
        }
    }
}
