/*
 * @test
 * @summary Test for ASMifier.visitIntInsn method to ensure correct string output.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitIntInsn();
    }

    public static void testVisitIntInsn() {
        ASMifier asmifier = new ASMifier();
        int opcode = 0; // Using 0 as a placeholder opcode for testing
        int operand = 1; // Example operand
        asmifier.visitIntInsn(opcode, operand);
        String lastLine = asmifier.text.get(asmifier.text.size() - 1);
        String expectedOpcodeName = "NOP"; // Assuming opcode 0 corresponds to "NOP" in OPCODES array
        if (!lastLine.contains(expectedOpcodeName) || !lastLine.contains("1")) {
            throw new AssertionError("visitIntInsn output does not match expected format. Output: " + lastLine);
        }
    }
}
