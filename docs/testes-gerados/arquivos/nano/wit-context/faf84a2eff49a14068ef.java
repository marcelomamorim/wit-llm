/* @test    @summary Test for AnnotationVisitor constructor with valid API values    @run main AnnotationVisitorWitupTest */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        try {
            // Test with ASM4 (assuming 4 is a valid constant, e.g., Opcodes.ASM4)
            AnnotationVisitor av1 = new AnnotationVisitor(4);
            // Test with ASM5 (assuming 5 is a valid constant)
            AnnotationVisitor av2 = new AnnotationVisitor(5);
            // Test with ASM6 (assuming 6 is a valid constant)
            AnnotationVisitor av3 = new AnnotationVisitor(6);
        } catch (Exception e) {
            throw new AssertionError("Valid API values should not throw exception", e);
        }
    }

    private static void testInvalidApiValues() {
        // Test with API less than Opcodes.ASM4 (assuming 4)
        try {
            new AnnotationVisitor(3);
            throw new AssertionError("Expected IllegalArgumentException for API < Opcodes.ASM4");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // Test with API greater than Opcodes.ASM6 (assuming 6)
        try {
            new AnnotationVisitor(7);
            throw new AssertionError("Expected IllegalArgumentException for API > Opcodes.ASM6");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
