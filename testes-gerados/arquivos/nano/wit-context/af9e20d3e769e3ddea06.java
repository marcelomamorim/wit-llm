/*
 * @test
 * @summary Test for MethodVisitor constructor with valid and invalid API values
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testApiBelowRange();
        testApiAboveRange();
    }

    static void testValidApi() {
        try {
            MethodVisitor mv = new MethodVisitor(ASM4);
            if (mv == null) {
                throw new AssertionError("MethodVisitor instance should not be null for valid API");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid API: " + e);
        }
    }

    static void testApiBelowRange() {
        try {
            new MethodVisitor(ASM3); // ASM3 < ASM4, should throw IllegalArgumentException
            throw new AssertionError("Expected IllegalArgumentException for API below range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testApiAboveRange() {
        try {
            new MethodVisitor(ASM7); // ASM7 > ASM6, should throw IllegalArgumentException
            throw new AssertionError("Expected IllegalArgumentException for API above range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    // Constants for ASM API levels based on context
    static final int ASM4 = 4;
    static final int ASM6 = 6;
    static final int ASM7 = 7;
}
