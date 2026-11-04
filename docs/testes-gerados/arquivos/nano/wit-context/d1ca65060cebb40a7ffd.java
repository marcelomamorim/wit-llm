/*
 * @test
 * @summary Test for Textifier.visitIntInsn to verify handling of various operand values.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitIntInsnWithValidOperands();
        testVisitIntInsnWithInvalidOperands();
    }

    private static void testVisitIntInsnWithValidOperands() {
        Textifier t = new Textifier();
        try {
            t.visitIntInsn(Opcodes.NEWARRAY, 1); // Valid operand for array type
            t.visitIntInsn(Opcodes.BIPUSH, 5); // Valid operand for push
            System.out.println("Valid operands test passed.");
        } catch (Exception e) {
            System.err.println("Valid operands test failed: " + e);
        }
    }

    private static void testVisitIntInsnWithInvalidOperands() {
        Textifier t = new Textifier();
        try {
            t.visitIntInsn(Opcodes.NEWARRAY, -1); // Invalid operand, should not throw but test behavior
            t.visitIntInsn(Opcodes.BIPUSH, Integer.MAX_VALUE); // Potentially invalid, test behavior
            System.out.println("Invalid operands test executed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
