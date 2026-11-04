/*
 * @test
 * @summary Checks that CheckAnnotationAdapter.visitEnd() rejects a second call and any later visit call with IllegalStateException
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testFirstVisitEndSucceeds();
        testSecondVisitEndThrows();
        testVisitAfterVisitEndThrows();
    }

    static void testFirstVisitEndSucceeds() {
        CheckAnnotationAdapter cav = new CheckAnnotationAdapter((AnnotationVisitor) null);
        cav.visitEnd();
    }

    static void testSecondVisitEndThrows() {
        CheckAnnotationAdapter cav = new CheckAnnotationAdapter((AnnotationVisitor) null);
        cav.visitEnd();
        boolean thrown = false;
        try {
            cav.visitEnd();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on second visitEnd()");
        }
    }

    static void testVisitAfterVisitEndThrows() {
        CheckAnnotationAdapter cav = new CheckAnnotationAdapter((AnnotationVisitor) null);
        cav.visitEnd();
        boolean thrown = false;
        try {
            cav.visit("value", Integer.valueOf(1));
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on visit() after visitEnd()");
        }
    }
}
