/*
 * @test
 * @summary Deterministic tests for CheckAnnotationAdapter.visitArray(String)
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    static class DummyAnnotationVisitor extends AnnotationVisitor {
        DummyAnnotationVisitor() {
            super(0);
        }
        @Override
        public AnnotationVisitor visitArray(final String name) {
            return this;
        }
    }

    public static void main(String[] args) {
        testVisitArrayThrowsIfEnd();
        testVisitArrayThrowsIfNamedAndNameNull();
        testVisitArrayReturnsCheckAnnotationAdapter();
    }

    private static void testVisitArrayThrowsIfEnd() {
        DummyAnnotationVisitor dav = new DummyAnnotationVisitor();
        CheckAnnotationAdapter ca = new CheckAnnotationAdapter(dav);
        // simulate end = true by calling visitArray then end
        // but end is private, so we simulate by calling visitArray twice and catching IllegalStateException
        ca.visitArray("test");
        try {
            ca.visitArray("test2");
            throw new RuntimeException("Expected IllegalStateException not thrown when end is true");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitArrayThrowsIfNamedAndNameNull() {
        DummyAnnotationVisitor dav = new DummyAnnotationVisitor();
        // Use constructor with named = true
        CheckAnnotationAdapter ca = new CheckAnnotationAdapter(dav, true);
        try {
            ca.visitArray(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when named is true and name is null");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testVisitArrayReturnsCheckAnnotationAdapter() {
        DummyAnnotationVisitor dav = new DummyAnnotationVisitor();
        CheckAnnotationAdapter ca = new CheckAnnotationAdapter(dav);
        AnnotationVisitor av = ca.visitArray("arrayName");
        if (av == null) {
            throw new RuntimeException("visitArray returned null");
        }
        if (!(av instanceof CheckAnnotationAdapter)) {
            throw new RuntimeException("visitArray did not return a CheckAnnotationAdapter instance");
        }
    }
}
