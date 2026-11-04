/*
 * @test
 * @summary Test constructor of SignatureVisitor for valid and invalid api values
 * @run main SignatureVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class SignatureVisitorWitupTest {
    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with valid api values: Opcodes.ASM4, ASM5, ASM6
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            SignatureVisitor sv = new SignatureVisitor(api);
            if (sv == null) {
                throw new AssertionError("Constructor returned null for valid api: " + api);
            }
        }
    }

    private static void testInvalidApiValues() {
        // Test with invalid api values less than ASM4
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM4 - 10, Opcodes.ASM6 + 1, Opcodes.ASM6 + 10};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new SignatureVisitor(api);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for api: " + api);
            }
        }
    }
}
