/*
 * @test
 * @summary Test for CheckMethodAdapter.visitEnd() method.
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndNormal();
        testVisitEndRepeated();
    }

    private static void testVisitEndNormal() {
        MethodVisitor mv = new DummyMethodVisitor();
        CheckMethodAdapter adapter = new CheckMethodAdapter(mv);
        try {
            adapter.visitEnd();
            System.out.println("testVisitEndNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitEndNormal failed: " + e);
        }
    }

    private static void testVisitEndRepeated() {
        MethodVisitor mv = new DummyMethodVisitor();
        CheckMethodAdapter adapter = new CheckMethodAdapter(mv);
        try {
            adapter.visitEnd();
            adapter.visitEnd(); // calling again to test idempotency or repeated call
            System.out.println("testVisitEndRepeated passed");
        } catch (Exception e) {
            System.err.println("testVisitEndRepeated failed: " + e);
        }
    }

    static class DummyMethodVisitor extends MethodVisitor {
        public DummyMethodVisitor() {
            super(0);
        }
        @Override
        public void visitEnd() {
            // no-op
        }
    }
}
