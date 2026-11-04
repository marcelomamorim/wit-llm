/*
 * @test
 * @summary Test visitIntInsn method of Textifier for basic opcode and operand handling
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
        if (!output1.contains("BIPUSH 123")) {
            throw new AssertionError("Expected output to contain 'BIPUSH 123' but was: " + output1);
        }

        // Test with NEWARRAY opcode and valid operand (e.g., T_BOOLEAN = 4)
        textifier.visitIntInsn(Opcodes.NEWARRAY, 4);
        String output2 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output2.contains("NEWARRAY boolean")) {
            throw new AssertionError("Expected output to contain 'NEWARRAY boolean' but was: " + output2);
        }

        // Test with NEWARRAY opcode and an operand outside TYPES array bounds (should fallback to integer string)
        int invalidOperand = 99;
        textifier.visitIntInsn(Opcodes.NEWARRAY, invalidOperand);
        String output3 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output3.contains("NEWARRAY 99")) {
            throw new AssertionError("Expected output to contain 'NEWARRAY 99' but was: " + output3);
        }

        // Test with opcode outside OPCODES array bounds (should throw ArrayIndexOutOfBoundsException)
        boolean caught = false;
        try {
            textifier.visitIntInsn(9999, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid opcode");
        }

        System.out.println("All tests passed.");
    }
}
