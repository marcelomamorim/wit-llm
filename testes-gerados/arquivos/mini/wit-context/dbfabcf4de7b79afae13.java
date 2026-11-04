/*
 * @test
 * @summary Test visitJumpInsn method of Textifier for basic functionality and label handling
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Label label = new Label();

        // Basic call with valid opcode and label
        textifier.visitJumpInsn(167 /* Opcodes.GOTO */, label);

        // Call with opcode 0 (not a valid opcode but allowed as int) and null label to check behavior
        textifier.visitJumpInsn(0, null);

        // Call with negative opcode to check no exception is thrown
        textifier.visitJumpInsn(-1, label);

        // Call with large opcode value to check no exception is thrown
        textifier.visitJumpInsn(255, label);

        // If no exceptions thrown, test passes
        System.out.println("Textifier.visitJumpInsn executed without exceptions");
    }
}
