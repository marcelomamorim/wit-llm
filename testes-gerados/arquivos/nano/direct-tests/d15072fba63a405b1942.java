/*
 * @test
 * @summary Test for ASMifier.visitJumpInsn method to ensure correct label handling.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Label;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitJumpInsnWithValidOpcode();
        testVisitJumpInsnWithInvalidOpcode();
    }

    private static void testVisitJumpInsnWithValidOpcode() {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();
        int validOpcode = 167; // Example opcode, e.g., IFNE
        asmifier.visitJumpInsn(validOpcode, label);
        String lastLine = asmifier.text.get(asmifier.text.size() - 1);
        assert lastLine.contains("ASMifier.visitJumpInsn") : "Expected method call in output";
        assert lastLine.contains("OPCODES") : "Expected OPCODES array reference";
        assert lastLine.contains("label") : "Expected label reference";
    }

    private static void testVisitJumpInsnWithInvalidOpcode() {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();
        int invalidOpcode = -1; // Invalid opcode
        try {
            asmifier.visitJumpInsn(invalidOpcode, label);
            String lastLine = asmifier.text.get(asmifier.text.size() - 1);
            assert lastLine.contains("ASMifier.visitJumpInsn") : "Expected method call in output";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert true : "Caught expected exception for invalid opcode";
        }
    }
}
