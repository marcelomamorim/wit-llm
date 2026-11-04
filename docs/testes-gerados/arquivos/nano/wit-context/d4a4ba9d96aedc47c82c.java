/*
 * @test
 * @summary Test for Textifier.visitTypeInsn handling of invalid input
 * @run main TextifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitTypeInsnWithInvalidType();
        testVisitTypeInsnWithNullType();
    }

    public static void testVisitTypeInsnWithInvalidType() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        String invalidType = "InvalidType";
        try {
            textifier.visitTypeInsn(jdk.internal.org.objectweb.asm.Opcodes.NEW, invalidType);
            // No exception expected, test passes if no exception thrown
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    public static void testVisitTypeInsnWithNullType() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        try {
            textifier.visitTypeInsn(jdk.internal.org.objectweb.asm.Opcodes.NEW, null);
            // Expecting NullPointerException or IllegalArgumentException
            throw new AssertionError("Expected exception for null type");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
