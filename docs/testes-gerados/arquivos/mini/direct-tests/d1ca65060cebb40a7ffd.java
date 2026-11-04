/*
 * @test
 * @summary Test Textifier.visitIntInsn method behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal opcode and operand
        textifier.visitIntInsn(Opcodes.BIPUSH, 123);
        String output1 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output1.contains("BIPUSH 123\n")) {
            throw new AssertionError("Expected output to contain 'BIPUSH 123\n' but was: " + output1);
        }

        // Test with NEWARRAY opcode and valid operand (TYPES index)
        // TYPES array is internal, but operand is used as index, test with 4 (T_INT)
        textifier.visitIntInsn(Opcodes.NEWARRAY, 4);
        String output2 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output2.contains("NEWARRAY int\n")) {
            throw new AssertionError("Expected output to contain 'NEWARRAY int\n' but was: " + output2);
        }

        // Test with NEWARRAY opcode and operand 0 (T_BOOLEAN)
        textifier.visitIntInsn(Opcodes.NEWARRAY, 0);
        String output3 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output3.contains("NEWARRAY boolean\n")) {
            throw new AssertionError("Expected output to contain 'NEWARRAY boolean\n' but was: " + output3);
        }

        // Test with opcode outside normal range (e.g. 0) and operand 0
        // Should not throw, but output will contain OPCODES[0] which is "NOP"
        textifier.visitIntInsn(0, 0);
        String output4 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output4.contains("NOP 0\n")) {
            throw new AssertionError("Expected output to contain 'NOP 0\n' but was: " + output4);
        }

        // Test with negative operand for non-NEWARRAY opcode
        textifier.visitIntInsn(Opcodes.BIPUSH, -1);
        String output5 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output5.contains("BIPUSH -1\n")) {
            throw new AssertionError("Expected output to contain 'BIPUSH -1\n' but was: " + output5);
        }
    }
}
