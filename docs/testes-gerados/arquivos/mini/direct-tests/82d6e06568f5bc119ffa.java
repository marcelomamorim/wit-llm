/*
 * @test
 * @summary Test ASMifier.visitVarInsn method for correct output formatting
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Clear any previous text
        asmifier.text.clear();

        // Use a valid opcode index within OPCODES array bounds (e.g., 0 for "NOP")
        int opcode = 0; // corresponds to "NOP" in OPCODES
        int varIndex = 5;

        asmifier.visitVarInsn(opcode, varIndex);

        // The text list should have exactly one entry
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 but was " + asmifier.text.size());
        }

        String output = asmifier.text.get(0);

        // The output should contain the visitor variable name and opcode mnemonic and var index
        if (!output.contains(asmifier.name + ".visitVarInsn(")) {
            throw new AssertionError("Output does not contain visitor variable name and method call: " + output);
        }

        // OPCODES[0] is "NOP" so output should contain "NOP"
        if (!output.contains("NOP")) {
            throw new AssertionError("Output does not contain expected opcode mnemonic 'NOP': " + output);
        }

        // Output should contain the variable index
        if (!output.contains(", 5")) {
            throw new AssertionError("Output does not contain expected variable index 5: " + output);
        }

        // Output should end with a newline character
        if (!output.endsWith("\n")) {
            throw new AssertionError("Output does not end with newline character: " + output);
        }
    }
}
