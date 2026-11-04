/*
 * @test
 * @summary Test visitJumpInsn method of ASMifier for correct output and label handling
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Label;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();

        // Call visitJumpInsn with a valid opcode and label
        asmifier.visitJumpInsn(167, label); // 167 = Opcodes.GOTO

        // The text list should contain one entry with the expected string
        if (asmifier.text.isEmpty()) {
            throw new AssertionError("text list should not be empty after visitJumpInsn");
        }

        String output = asmifier.text.get(asmifier.text.size() - 1);

        // Check that output contains the opcode name and label representation
        if (!output.contains("GOTO")) {
            throw new AssertionError("Output does not contain expected opcode name 'GOTO': " + output);
        }

        // Check that output contains the visitor variable name
        if (!output.contains(asmifier.name)) {
            throw new AssertionError("Output does not contain visitor variable name: " + asmifier.name);
        }

        // Check that output ends with ");\n"
        if (!output.endsWith(");\n")) {
            throw new AssertionError("Output does not end with ');\n': " + output);
        }

        // Check that the label was declared (labelNames map should contain the label)
        if (!asmifier.labelNames.containsKey(label)) {
            throw new AssertionError("Label was not declared in labelNames map");
        }

        System.out.println("Test passed.");
    }
}
