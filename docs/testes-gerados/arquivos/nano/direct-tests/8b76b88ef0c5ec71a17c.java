/*
 * @test
 * @summary Test CheckAnnotationAdapter.visitEnd() method for correct behavior.
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndCallsEndFlagAndDelegates();
        testVisitEndWithoutDelegate();
    }

    private static void testVisitEndCallsEndFlagAndDelegates() {
        final boolean[] delegateCalled = {false};
        class DummyVisitor extends jdk.internal.org.objectweb.asm.AnnotationVisitor {
            boolean visitedEnd = false;
            DummyVisitor() {
                super(jdk.internal.org.objectweb.asm.Opcodes.ASM9);
            }
            @Override
            public void visitEnd() {
                visitedEnd = true;
                delegateCalled[0] = true;
            }
        }
        DummyVisitor delegate = new DummyVisitor();
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(delegate);
        adapter.visitEnd();
        assert adapter.end : "visitEnd() should set end to true";
        assert delegate.visitedEnd : "Delegate's visitEnd() should be called";
        assert delegateCalled[0] : "Delegate's visitEnd() should be invoked";
    }

    private static void testVisitEndWithoutDelegate() {
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(null);
        adapter.visitEnd();
        assert adapter.end : "visitEnd() should set end to true even if delegate is null";
    }
}
