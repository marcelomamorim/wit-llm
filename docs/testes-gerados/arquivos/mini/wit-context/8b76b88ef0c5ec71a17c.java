/*
 * @test
 * @summary Test CheckAnnotationAdapter.visitEnd() for correct IllegalStateException on repeated end
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndThrowsIfAlreadyEnded();
        testVisitEndDelegatesToWrappedVisitor();
    }

    private static void testVisitEndThrowsIfAlreadyEnded() {
        // Create a CheckAnnotationAdapter with a dummy AnnotationVisitor
        AnnotationVisitor dummyAv = new AnnotationVisitor(0) {};
        CheckAnnotationAdapter caa = new CheckAnnotationAdapter(dummyAv);

        // First call to visitEnd should succeed
        caa.visitEnd();

        // Second call to visitEnd should throw IllegalStateException because end is true
        boolean threw = false;
        try {
            caa.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second visitEnd call");
        }
    }

    private static void testVisitEndDelegatesToWrappedVisitor() {
        // Create a custom AnnotationVisitor that records if visitEnd was called
        class RecordingAnnotationVisitor extends AnnotationVisitor {
            boolean visitedEnd = false;
            RecordingAnnotationVisitor() {
                super(0);
            }
            @Override
            public void visitEnd() {
                visitedEnd = true;
            }
        }

        RecordingAnnotationVisitor recordingAv = new RecordingAnnotationVisitor();
        CheckAnnotationAdapter caa = new CheckAnnotationAdapter(recordingAv);

        caa.visitEnd();

        if (!recordingAv.visitedEnd) {
            throw new AssertionError("visitEnd was not delegated to wrapped AnnotationVisitor");
        }
    }
}
