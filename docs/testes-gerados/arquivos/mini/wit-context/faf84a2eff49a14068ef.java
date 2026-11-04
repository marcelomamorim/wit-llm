/*
 * @test
 * @summary Test AnnotationVisitor constructor with api argument for valid and invalid values
 * @run main AnnotationVisitorWitupTest
 */

public class AnnotationVisitorWitupTest {

    // Minimal Opcodes constants as per context (ASM4=4, ASM5=5, ASM6=6)
    private static final int ASM4 = 4;
    private static final int ASM5 = 5;
    private static final int ASM6 = 6;

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
        System.out.println("All tests passed.");
    }

    private static void testValidApiValues() {
        // Test with valid api values: ASM4, ASM5, ASM6
        new TestAnnotationVisitor(ASM4);
        new TestAnnotationVisitor(ASM5);
        new TestAnnotationVisitor(ASM6);
    }

    private static void testInvalidApiValues() {
        // Test with invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {ASM4 - 1, ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            try {
                new TestAnnotationVisitor(api);
                throw new AssertionError("Expected IllegalArgumentException for api=" + api);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        }
    }

    // Concrete subclass to test the abstract AnnotationVisitor
    private static class TestAnnotationVisitor extends jdk.internal.org.objectweb.asm.AnnotationVisitor {

        public TestAnnotationVisitor(int api) {
            super(api);
        }

        public TestAnnotationVisitor(int api, jdk.internal.org.objectweb.asm.AnnotationVisitor av) {
            super(api, av);
        }

        @Override
        public void visit(String name, Object value) {
            // no-op
        }

        @Override
        public void visitEnum(String name, String descriptor, String value) {
            // no-op
        }

        @Override
        public jdk.internal.org.objectweb.asm.AnnotationVisitor visitAnnotation(String name, String descriptor) {
            return null;
        }

        @Override
        public jdk.internal.org.objectweb.asm.AnnotationVisitor visitArray(String name) {
            return null;
        }

        @Override
        public void visitEnd() {
            // no-op
        }
    }
}
