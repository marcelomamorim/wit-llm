/*
 * @test
 * @summary Test visitJumpInsn method of Textifier class for correct label appending.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitJumpInsn();
    }

    public static void testVisitJumpInsn() {
        Textifier textifier = new Textifier();
        Label label = new Label();
        int opcode = 167; // Example opcode (e.g., IFNE)
        textifier.visitJumpInsn(opcode, label);
        String output = textifier.text.get(textifier.text.size() - 1);
        boolean containsOpcode = output.contains("" + Textifier.OPCODES[opcode]);
        boolean containsLabel = output.contains(label.toString());
        if (!containsOpcode || !containsLabel) {
            throw new AssertionError("Output does not contain expected opcode or label. Output: " + output);
        }
        System.out.println("visitJumpInsn test passed.");
    }
}
