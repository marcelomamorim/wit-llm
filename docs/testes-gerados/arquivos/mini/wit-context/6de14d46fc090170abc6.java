/*
 * @test
 * @summary Test visitIntInsn method of ASMifier for correct output and no exceptions
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with opcode NEWARRAY and valid operand (e.g., T_BOOLEAN = 4)
        asmifier.visitIntInsn(188 /* Opcodes.NEWARRAY */, 4);
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitIntInsn");
        }
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("visitIntInsn") || !lastLine.contains("NEWARRAY") || !lastLine.contains("BOOLEAN")) {
            throw new AssertionError("Output line does not contain expected opcode and operand info: " + lastLine);
        }

        // Test with opcode other than NEWARRAY and arbitrary operand
        asmifier.visitIntInsn(21 /* Opcodes.ILOAD */, 5);
        text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitIntInsn");
        }
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("visitIntInsn") || !lastLine.contains("ILOAD") || !lastLine.contains("5")) {
            throw new AssertionError("Output line does not contain expected opcode and operand info: " + lastLine);
        }

        // Test with invalid opcode index (negative) - expect ArrayIndexOutOfBoundsException
        boolean caught = false;
        try {
            asmifier.visitIntInsn(-1, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid opcode index");
        }

        // Test with invalid opcode index (too large) - expect ArrayIndexOutOfBoundsException
        caught = false;
        try {
            asmifier.visitIntInsn(9999, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid opcode index");
        }

        System.out.println("All ASMifier.visitIntInsn tests passed.");
    }
}
