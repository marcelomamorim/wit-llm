/*
 * @test
 * @summary Test constructor FieldVisitor(int api, FieldVisitor fv) for valid and invalid api values
 * @run main FieldVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with valid api values: ASM4, ASM5, ASM6
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            FieldVisitor fv = new FieldVisitor(api, null) {
                // No override needed for this test
            };
            if (fv.api != api) {
                throw new AssertionError("FieldVisitor.api field not set correctly for api=" + api);
            }
            if (fv.fv != null) {
                throw new AssertionError("FieldVisitor.fv field should be null when constructed with null");
            }
        }
    }

    private static void testInvalidApiValues() {
        // Test with invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new FieldVisitor(api, null) {
                    // No override needed
                };
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for api=" + api);
            }
        }
    }
}
