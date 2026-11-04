/*
 * @test
 * @summary Test visitInsn method of ASMifier for basic opcode handling and output
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a valid opcode (e.g., Opcodes.NOP = 0)
        asmifier.visitInsn(0);
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitInsn");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("visitInsn") || !output.contains("NOP")) {
            throw new AssertionError("Output should contain visitInsn and opcode name NOP: " + output);
        }

        // Test with another valid opcode (e.g., Opcodes.RETURN = 177)
        asmifier.visitInsn(177);
        text = asmifier.getText();
        output = text.get(text.size() - 1);
        if (!output.contains("visitInsn") || !output.contains("RETURN")) {
            throw new AssertionError("Output should contain visitInsn and opcode name RETURN: " + output);
        }

        // Test with an invalid opcode index (negative) - expect ArrayIndexOutOfBoundsException
        boolean caught = false;
        try {
            asmifier.visitInsn(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("visitInsn should throw ArrayIndexOutOfBoundsException for negative opcode");
        }

        // Test with an invalid opcode index (too large) - expect ArrayIndexOutOfBoundsException
        caught = false;
        try {
            asmifier.visitInsn(256); // beyond OPCODES array length
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("visitInsn should throw ArrayIndexOutOfBoundsException for too large opcode");
        }

        System.out.println("ASMifierWitupTest passed");
    }
}
