/*
 * @test
 * @summary Test ASMifier.visitInsn(int) method for correct output
 * @run main ASMifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a valid opcode (NOP = 0)
        asmifier.visitInsn(0);
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitInsn");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("visitInsn")) {
            throw new AssertionError("Output line should contain 'visitInsn': " + line);
        }
        if (!line.contains("NOP")) {
            throw new AssertionError("Output line should contain opcode name 'NOP': " + line);
        }

        // Test with another valid opcode (RETURN = 177)
        asmifier.visitInsn(177);
        text = asmifier.getText();
        line = text.get(text.size() - 1);
        if (!line.contains("RETURN")) {
            throw new AssertionError("Output line should contain opcode name 'RETURN': " + line);
        }

        // Test with an opcode at the upper bound of OPCODES array
        // The OPCODES array length is 202 in ASM 6.0 (JDK 11), test last valid index
        int maxOpcode = 201;
        asmifier.visitInsn(maxOpcode);
        text = asmifier.getText();
        line = text.get(text.size() - 1);
        if (!line.contains("visitInsn")) {
            throw new AssertionError("Output line should contain 'visitInsn' for max opcode: " + line);
        }

        // Test with an opcode out of range (negative) - expect ArrayIndexOutOfBoundsException
        boolean caught = false;
        try {
            asmifier.visitInsn(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative opcode");
        }

        // Test with an opcode out of range (too large) - expect ArrayIndexOutOfBoundsException
        caught = false;
        try {
            asmifier.visitInsn(202);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for opcode > max");
        }
    }
}
