/*
 * @test
 * @summary Test AnnotationVisitor single-argument constructor behavior
 * @run main AnnotationVisitorWitupTest
 */

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        // Test construction with valid api values
        testConstructorWithApi(262144); // Opcodes.ASM6
        testConstructorWithApi(327680); // Opcodes.ASM7 (not in JDK11 but safe as int)
        testConstructorWithApi(458752); // Opcodes.ASM9 (not in JDK11 but safe as int)

        // Test construction with zero and negative api values
        testConstructorWithApi(0);
        testConstructorWithApi(-1);

        System.out.println("All tests passed.");
    }

    private static void testConstructorWithApi(int api) {
        // Create anonymous subclass since AnnotationVisitor is abstract
        AnnotationVisitor av = new AnnotationVisitor(api) {
            @Override
            public void visit(String name, Object value) {}
            @Override
            public void visitEnum(String name, String descriptor, String value) {}
            @Override
            public AnnotationVisitor visitAnnotation(String name, String descriptor) { return null; }
            @Override
            public AnnotationVisitor visitArray(String name) { return null; }
            @Override
            public void visitEnd() {}
        };

        if (av.api != api) {
            throw new AssertionError("api field mismatch: expected " + api + " but got " + av.api);
        }

        if (av.av != null) {
            throw new AssertionError("av field should be null when constructed with single-arg constructor");
        }
    }
}
