/*
 * @test
 * @summary Test visitVarInsn method of ASMifier for correct output and no exceptions
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with valid opcode and var
        asmifier.visitVarInsn(0, 0); // opcode 0 is valid (NOP)
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitVarInsn call");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("visitVarInsn") || !output.contains("0")) {
            throw new AssertionError("Output does not contain expected visitVarInsn call");
        }

        // Test with opcode at upper bound (assuming OPCODES length > 0)
        int maxOpcode = ASMifier.OPCODES.length - 1;
        asmifier.visitVarInsn(maxOpcode, 10);
        text = asmifier.getText();
        output = text.get(text.size() - 1);
        if (!output.contains(ASMifier.OPCODES[maxOpcode]) || !output.contains("10")) {
            throw new AssertionError("Output does not contain expected opcode or var");
        }

        // Test with negative var (should not throw, just output)
        asmifier.visitVarInsn(0, -1);
        text = asmifier.getText();
        output = text.get(text.size() - 1);
        if (!output.contains("-1")) {
            throw new AssertionError("Output does not contain negative var value");
        }

        // Test with opcode out of range (negative) - expect ArrayIndexOutOfBoundsException
        boolean caught = false;
        try {
            asmifier.visitVarInsn(-1, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative opcode");
        }

        // Test with opcode out of range (too large) - expect ArrayIndexOutOfBoundsException
        caught = false;
        try {
            asmifier.visitVarInsn(ASMifier.OPCODES.length, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for opcode out of range");
        }

        System.out.println("All tests passed.");
    }
}
