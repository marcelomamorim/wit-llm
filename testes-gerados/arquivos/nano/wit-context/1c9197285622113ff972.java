/*
 * @test
 * @summary Test for ClassVisitor constructor with valid API values
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    static void testValidApiValues() {
        try {
            // Test with ASM4
            ClassVisitor cv1 = new ClassVisitor(4);
            if (cv1 == null) {
                throw new AssertionError("ClassVisitor instance should not be null for ASM4");
            }
            // Test with ASM5
            ClassVisitor cv2 = new ClassVisitor(5);
            if (cv2 == null) {
                throw new AssertionError("ClassVisitor instance should not be null for ASM5");
            }
            // Test with ASM6
            ClassVisitor cv3 = new ClassVisitor(6);
            if (cv3 == null) {
                throw new AssertionError("ClassVisitor instance should not be null for ASM6");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid API values test", e);
        }
    }

    static void testInvalidApiValues() {
        // Test with API less than ASM4
        try {
            new ClassVisitor(3);
            throw new AssertionError("Expected IllegalArgumentException for API=3");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        // Test with API greater than ASM6
        try {
            new ClassVisitor(7);
            throw new AssertionError("Expected IllegalArgumentException for API=7");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
