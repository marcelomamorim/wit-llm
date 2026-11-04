/*
 * @test
 * @summary Test constructor of AnnotationVisitor for valid and invalid api values
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Test with valid api values: ASM4, ASM5, ASM6
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            AnnotationVisitor av = new AnnotationVisitor(api, null) {
                // No override needed for this test
            };
            if (av.api != api) {
                throw new AssertionError("api field not set correctly for api=" + api);
            }
            if (av.av != null) {
                throw new AssertionError("av field should be null when passed null");
            }
        }
    }

    private static void testInvalidApiValues() {
        // Test with invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new AnnotationVisitor(api, null) {
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
