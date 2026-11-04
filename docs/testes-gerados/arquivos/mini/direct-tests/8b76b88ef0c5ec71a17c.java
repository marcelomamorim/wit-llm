/*
 * @test
 * @summary Test CheckAnnotationAdapter.visitEnd() sets end flag and delegates visitEnd
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    static class DummyAnnotationVisitor extends AnnotationVisitor {
        boolean visitEndCalled = false;

        DummyAnnotationVisitor() {
            super(0);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        // Test 1: visitEnd sets end flag and calls delegate visitEnd
        DummyAnnotationVisitor dav = new DummyAnnotationVisitor();
        CheckAnnotationAdapter ca = new CheckAnnotationAdapter(dav);

        // Before visitEnd, visitEndCalled should be false
        if (dav.visitEndCalled) {
            throw new Error("visitEndCalled should be false before visitEnd");
        }

        ca.visitEnd();

        // After visitEnd, delegate's visitEnd should be called
        if (!dav.visitEndCalled) {
            throw new Error("visitEnd was not delegated to underlying AnnotationVisitor");
        }

        // The end flag is private, so we cannot check it directly.
        // But calling visitEnd twice should cause an error due to checkEnd() in visitEnd.
        boolean threw = false;
        try {
            ca.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Calling visitEnd twice should throw IllegalStateException");
        }

        // Test 2: visitEnd with null delegate
        CheckAnnotationAdapter ca2 = new CheckAnnotationAdapter(null);
        ca2.visitEnd(); // should not throw

        // Calling visitEnd twice on ca2 should throw
        threw = false;
        try {
            ca2.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Calling visitEnd twice on adapter with null delegate should throw IllegalStateException");
        }
    }
}
