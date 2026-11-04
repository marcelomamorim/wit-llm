/*
 * @test
 * @summary Test CheckMethodAdapter.visitEnd() for correct behavior and IllegalStateException on repeated endMethod
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Label;
import java.util.HashMap;
import java.util.Map;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visitEndCalled = false;
        DummyMethodVisitor() {
            super(0);
        }
        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitEndSetsEndMethodAndCallsSuper();
        testVisitEndThrowsOnRepeatedCall();
    }

    private static void testVisitEndSetsEndMethodAndCallsSuper() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv, new HashMap<Label, Integer>());

        // Initially, endMethod should be false (not accessible, so we test behavior)
        // Call visitEnd once
        cma.visitEnd();

        // The super.visitEnd() should have been called, so DummyMethodVisitor.visitEndCalled true
        if (!dmv.visitEndCalled) {
            throw new AssertionError("super.visitEnd() was not called by CheckMethodAdapter.visitEnd()");
        }

        // Calling visitEnd second time should throw IllegalStateException
        // This is tested in another method
    }

    private static void testVisitEndThrowsOnRepeatedCall() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv, new HashMap<Label, Integer>());

        cma.visitEnd(); // first call should succeed

        boolean threw = false;
        try {
            cma.visitEnd(); // second call should throw IllegalStateException
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on repeated visitEnd call");
        }
    }
}
