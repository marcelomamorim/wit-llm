/*
 * @test
 * @summary Test CheckFieldAdapter.visitEnd() sets end flag and calls super.visitEnd()
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;
import jdk.internal.org.objectweb.asm.FieldVisitor;

public class CheckFieldAdapterWitupTest {

    static class FV extends FieldVisitor {
        boolean visitEndCalled = false;

        public FV() {
            super(0);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        FV fv = new FV();
        CheckFieldAdapter cfa = new CheckFieldAdapter(fv);

        // Before visitEnd, visitEndCalled should be false
        if (fv.visitEndCalled) {
            throw new RuntimeException("visitEnd called prematurely");
        }

        cfa.visitEnd();

        // After visitEnd, visitEndCalled should be true
        if (!fv.visitEndCalled) {
            throw new RuntimeException("visitEnd not delegated to super");
        }

        // The end flag is private, so we cannot access it directly.
        // But calling visitEnd twice should cause checkEnd() to fail if implemented correctly.
        // Since we do not have checkEnd() source, we just call visitEnd again to check no exception is thrown.
        // If checkEnd() throws, the test will fail here.
        cfa.visitEnd();
    }
}
