/*
 * @test
 * @summary Test CheckAnnotationAdapter.visitArray behavior
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    static class DummyAnnotationVisitor extends AnnotationVisitor {
        boolean visitArrayCalled = false;
        String lastName = null;

        public DummyAnnotationVisitor() {
            super(0);
        }

        @Override
        public AnnotationVisitor visitArray(final String name) {
            visitArrayCalled = true;
            lastName = name;
            return null;
        }
    }

    public static void main(String[] args) {
        // Test with null underlying AnnotationVisitor
        CheckAnnotationAdapter ca1 = new CheckAnnotationAdapter(null);
        AnnotationVisitor av1 = ca1.visitArray("testName");
        if (av1 == null) {
            System.out.println("PASS: visitArray returns null when underlying av is null");
        } else {
            throw new RuntimeException("FAIL: visitArray should return null when underlying av is null");
        }

        // Test with a dummy underlying AnnotationVisitor
        DummyAnnotationVisitor dummy = new DummyAnnotationVisitor();
        CheckAnnotationAdapter ca2 = new CheckAnnotationAdapter(dummy);
        AnnotationVisitor av2 = ca2.visitArray("arrayName");
        if (dummy.visitArrayCalled && "arrayName".equals(dummy.lastName)) {
            System.out.println("PASS: visitArray delegates call with correct name");
        } else {
            throw new RuntimeException("FAIL: visitArray did not delegate correctly");
        }

        // Test with null name argument
        DummyAnnotationVisitor dummy2 = new DummyAnnotationVisitor();
        CheckAnnotationAdapter ca3 = new CheckAnnotationAdapter(dummy2);
        try {
            ca3.visitArray(null);
            System.out.println("PASS: visitArray accepts null name without throwing");
        } catch (Exception e) {
            throw new RuntimeException("FAIL: visitArray threw exception on null name", e);
        }
    }
}
