/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitJumpInsn method
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Label label = new Label();

        // Test with opcode 0 (NOP) and a new label
        textifier.visitJumpInsn(0, label);
        String output0 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output0.contains("NOP") || !output0.contains("Label")) {
            throw new AssertionError("Output does not contain expected opcode or label for opcode 0");
        }

        // Test with opcode 167 (GOTO) and a new label
        textifier.visitJumpInsn(167, label);
        String output167 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output167.contains("GOTO") || !output167.contains("Label")) {
            throw new AssertionError("Output does not contain expected opcode or label for opcode 167");
        }

        // Test with opcode 200 (JSR_W) and a new label
        textifier.visitJumpInsn(200, label);
        String output200 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output200.contains("JSR_W") || !output200.contains("Label")) {
            throw new AssertionError("Output does not contain expected opcode or label for opcode 200");
        }

        // Test with opcode 255 (invalid opcode) and a new label
        // Should not throw exception, but output will contain null or invalid opcode string
        textifier.visitJumpInsn(255, label);
        String output255 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output255.contains("null") && !output255.contains("Label")) {
            throw new AssertionError("Output does not contain expected label or null for invalid opcode 255");
        }
    }
}
