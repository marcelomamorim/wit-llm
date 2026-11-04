/*
 * @test
 * @summary Test for CheckClassAdapter.visitEnd() method behavior.
 * @run main CheckClassAdapterWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndNormal();
        testVisitEndWithStartFalse();
    }

    private static void testVisitEndNormal() {
        CheckClassAdapter adapter = new CheckClassAdapter(new DummyClassVisitor());
        try {
            adapter.visitEnd();
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException in normal case", e);
        }
    }

    private static void testVisitEndWithStartFalse() {
        DummyClassVisitor dummyVisitor = new DummyClassVisitor();
        dummyVisitor.setStartFlag(false);
        CheckClassAdapter adapter = new CheckClassAdapter(dummyVisitor);
        try {
            adapter.visitEnd();
            // Expecting IllegalStateException due to guard condition
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static class DummyClassVisitor extends jdk.internal.org.objectweb.asm.ClassVisitor {
        private boolean startFlag = true;
        private boolean endFlag = false;

        public DummyClassVisitor() {
            super(0);
        }

        public void setStartFlag(boolean value) {
            this.startFlag = value;
        }

        @Override
        public void visitEnd() {
            if (!startFlag) {
                throw new IllegalStateException();
            }
            super.visitEnd();
        }
    }
}
