/* @test    @summary Test for MethodVisitor constructor with valid and invalid API values    @run main MethodVisitorWitupTest */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApiBelow();
        testInvalidApiAbove();
    }

    private static void testValidApi() {
        try {
            MethodVisitor mv = new MethodVisitor(Opcodes.ASM4);
            // Expect no exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid API Opcodes.ASM4", e);
        }
        try {
            MethodVisitor mv = new MethodVisitor(Opcodes.ASM6);
            // Expect no exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid API Opcodes.ASM6", e);
        }
    }

    private static void testInvalidApiBelow() {
        try {
            MethodVisitor mv = new MethodVisitor(-1);
            throw new AssertionError("Expected IllegalArgumentException for API below Opcodes.ASM4");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for API below Opcodes.ASM4", e);
        }
    }

    private static void testInvalidApiAbove() {
        try {
            MethodVisitor mv = new MethodVisitor(100);
            throw new AssertionError("Expected IllegalArgumentException for API above Opcodes.ASM6");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for API above Opcodes.ASM6", e);
        }
    }
}
