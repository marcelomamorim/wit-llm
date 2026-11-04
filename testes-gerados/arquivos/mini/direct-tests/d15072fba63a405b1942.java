/*
 * @test
 * @summary Test ASMifier.visitJumpInsn method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();

        // Call visitJumpInsn with a valid opcode and label
        asmifier.visitJumpInsn(167, label); // 167 = Opcodes.GOTO

        // The text list should contain one entry with the expected string
        if (asmifier.text.isEmpty()) {
            throw new Error("text list is empty after visitJumpInsn");
        }

        String output = asmifier.text.get(0);

        // Check that output contains the opcode name "GOTO" and label name
        if (!output.contains("GOTO")) {
            throw new Error("Output does not contain expected opcode name 'GOTO': " + output);
        }

        // The label name should be declared and appended, so output should contain some label identifier
        // We check that output contains "label" (the default label name prefix in ASMifier)
        if (!output.contains("label")) {
            throw new Error("Output does not contain label identifier: " + output);
        }

        // Call visitJumpInsn with another opcode to verify multiple calls
        Label label2 = new Label();
        asmifier.visitJumpInsn(198, label2); // 198 = Opcodes.IFNULL

        if (asmifier.text.size() != 2) {
            throw new Error("text list size expected 2 but was " + asmifier.text.size());
        }

        String output2 = asmifier.text.get(1);

        if (!output2.contains("IFNULL")) {
            throw new Error("Output does not contain expected opcode name 'IFNULL': " + output2);
        }

        if (!output2.contains("label")) {
            throw new Error("Output does not contain label identifier in second call: " + output2);
        }
    }
}
