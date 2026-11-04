/*
 * @test
 * @summary Test ClassVisitor constructor with valid and invalid api versions
 * @run main ClassVisitorWitupTest
 */

public class ClassVisitorWitupTest {

    static final int ASM4 = 4;
    static final int ASM5 = 5;
    static final int ASM6 = 6;

    // Minimal stub for ClassVisitor to allow construction
    static class ClassVisitor {
        protected final int api;
        protected ClassVisitor cv;

        public ClassVisitor(final int api, final ClassVisitor cv) {
            if (api < ASM4 || api > ASM6) {
                throw new IllegalArgumentException();
            }
            this.api = api;
            this.cv = cv;
        }
    }

    public static void main(String[] args) {
        testValidApis();
        testInvalidApis();
        System.out.println("All tests passed.");
    }

    private static void testValidApis() {
        // Test with ASM4
        ClassVisitor cv1 = new ClassVisitor(ASM4, null);
        if (cv1.api != ASM4) {
            throw new AssertionError("api field not set correctly for ASM4");
        }

        // Test with ASM5
        ClassVisitor cv2 = new ClassVisitor(ASM5, cv1);
        if (cv2.api != ASM5) {
            throw new AssertionError("api field not set correctly for ASM5");
        }
        if (cv2.cv != cv1) {
            throw new AssertionError("cv field not set correctly for ASM5");
        }

        // Test with ASM6
        ClassVisitor cv3 = new ClassVisitor(ASM6, null);
        if (cv3.api != ASM6) {
            throw new AssertionError("api field not set correctly for ASM6");
        }
    }

    private static void testInvalidApis() {
        // Test with api less than ASM4
        try {
            new ClassVisitor(ASM4 - 1, null);
            throw new AssertionError("Expected IllegalArgumentException for api < ASM4");
        } catch (IllegalArgumentException expected) {
        }

        // Test with api greater than ASM6
        try {
            new ClassVisitor(ASM6 + 1, null);
            throw new AssertionError("Expected IllegalArgumentException for api > ASM6");
        } catch (IllegalArgumentException expected) {
        }
    }
}
