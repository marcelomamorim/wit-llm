/*
 * @test
 * @summary Test visitEnd method of CheckMethodAdapter for correct state change and delegation
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visitEndCalled = false;

        public DummyMethodVisitor() {
            super(0);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);

        // Before visitEnd, endMethod should be false (cannot access directly, so test effect)
        // Call visitEnd
        cma.visitEnd();

        // Check that DummyMethodVisitor.visitEnd was called
        if (!dmv.visitEndCalled) {
            throw new AssertionError("super.visitEnd() was not called");
        }

        // The field endMethod is private, so we cannot check it directly.
        // But calling visitEnd twice should not cause exceptions or errors.
        cma.visitEnd();

        System.out.println("Test passed");
    }
}
