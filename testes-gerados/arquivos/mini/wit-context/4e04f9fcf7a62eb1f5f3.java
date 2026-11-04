/*
 * @test
 * @summary Test constructor FieldVisitor(int api) for valid and invalid api values
 * @run main FieldVisitorWitupTest
 */

public class FieldVisitorWitupTest {

    // Minimal Opcodes constants as per context (ASM4=4, ASM5=5, ASM6=6)
    private static final int ASM4 = 4;
    private static final int ASM5 = 5;
    private static final int ASM6 = 6;

    public static void main(String[] args) {
        testValidApis();
        testInvalidApis();
        System.out.println("All tests passed.");
    }

    private static void testValidApis() {
        // Test with valid api values: ASM4, ASM5, ASM6
        new jdk.internal.org.objectweb.asm.FieldVisitor(ASM4);
        new jdk.internal.org.objectweb.asm.FieldVisitor(ASM5);
        new jdk.internal.org.objectweb.asm.FieldVisitor(ASM6);
    }

    private static void testInvalidApis() {
        // Test with invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {ASM4 - 1, ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1};
        for (int api : invalidApis) {
            try {
                new jdk.internal.org.objectweb.asm.FieldVisitor(api);
                throw new AssertionError("Expected IllegalArgumentException for api=" + api);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        }
    }
}
