/*
 * @test
 * @summary Test MethodVisitor(int api) constructor for valid and invalid api values
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {
    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
        System.out.println("All tests passed.");
    }

    private static void testValidApiValues() {
        // Valid api values are Opcodes.ASM4, ASM5, ASM6
        // According to the expath, api must be between ASM4 and ASM6 inclusive
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            MethodVisitor mv = new MethodVisitor(api);
            if (mv == null) {
                throw new AssertionError("Constructor returned null for valid api: " + api);
            }
        }
    }

    private static void testInvalidApiValues() {
        // Values less than ASM4 and greater than ASM6 should throw IllegalArgumentException
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new MethodVisitor(api);
            } catch (IllegalArgumentException e) {
                thrown = true;
            } catch (Throwable t) {
                throw new AssertionError("Unexpected exception type for api " + api + ": " + t);
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for api: " + api);
            }
        }
    }
}
