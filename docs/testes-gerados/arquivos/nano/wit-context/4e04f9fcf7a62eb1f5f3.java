/* @test    @summary Test for FieldVisitor constructor with valid API values    @run main FieldVisitorWitupTest */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        try {
            // Test with ASM4
            FieldVisitor fv1 = new FieldVisitor(Opcodes.ASM4);
            // Test with ASM5
            FieldVisitor fv2 = new FieldVisitor(Opcodes.ASM5);
            // Test with ASM6
            FieldVisitor fv3 = new FieldVisitor(Opcodes.ASM6);
        } catch (Exception e) {
            throw new AssertionError("Valid API values should not throw exceptions", e);
        }
    }

    private static void testInvalidApiValues() {
        int[] invalidValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int invalidApi : invalidValues) {
            if (invalidApi != Opcodes.ASM4 && invalidApi != Opcodes.ASM5 && invalidApi != Opcodes.ASM6) {
                try {
                    new FieldVisitor(invalidApi);
                    throw new AssertionError("Expected IllegalArgumentException for api=" + invalidApi);
                } catch (IllegalArgumentException e) {
                    // Expected exception, test passes for this case
                } catch (Exception e) {
                    throw new AssertionError("Unexpected exception type for api=" + invalidApi, e);
                }
            }
        }
    }
}
