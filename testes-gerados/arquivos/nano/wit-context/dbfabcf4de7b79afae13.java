/*
 * @test
 * @summary Test visitJumpInsn method for exception paths and normal behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitJumpInsnValidOpcode();
        testVisitJumpInsnInvalidOpcode();
    }

    private static void testVisitJumpInsnValidOpcode() {
        Textifier t = new Textifier();
        Label label = new Label();
        try {
            t.visitJumpInsn(0, label); // Using opcode 0 as a valid index for testing
            System.out.println("testVisitJumpInsnValidOpcode passed");
        } catch (Exception e) {
            System.err.println("testVisitJumpInsnValidOpcode failed: " + e);
        }
    }

    private static void testVisitJumpInsnInvalidOpcode() {
        Textifier t = new Textifier();
        Label label = new Label();
        try {
            t.visitJumpInsn(-1, label); // Invalid opcode to test exception path
            System.err.println("testVisitJumpInsnInvalidOpcode failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testVisitJumpInsnInvalidOpcode passed");
        } catch (Exception e) {
            System.err.println("testVisitJumpInsnInvalidOpcode failed: " + e);
        }
    }
}
