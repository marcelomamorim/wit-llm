/*
 * @test
 * @summary Test MethodVisitor constructor with valid and invalid api values
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {
    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with valid api values: Opcodes.ASM4, ASM5, ASM6
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            MethodVisitor mv = new MethodVisitor(api, null);
            if (mv == null) {
                throw new AssertionError("Constructor returned null for valid api: " + api);
            }
        }
    }

    private static void testInvalidApiValues() {
        // Test with invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new MethodVisitor(api, null);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for api: " + api);
            }
        }
    }
}
