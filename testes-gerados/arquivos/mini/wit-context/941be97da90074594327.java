/*
 * @test
 * @summary Test CheckModuleAdapter.visitEnd() behavior including IllegalStateException on repeated calls
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    static class DummyModuleVisitor extends ModuleVisitor {
        boolean visitEndCalled = false;

        public DummyModuleVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitEndNormal();
        testVisitEndThrowsOnSecondCall();
    }

    private static void testVisitEndNormal() {
        DummyModuleVisitor dummy = new DummyModuleVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummy, false);
        adapter.visitEnd();
        if (!dummy.visitEndCalled) {
            throw new AssertionError("super.visitEnd() was not called on visitEnd()");
        }
    }

    private static void testVisitEndThrowsOnSecondCall() {
        DummyModuleVisitor dummy = new DummyModuleVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummy, false);
        adapter.visitEnd();
        boolean threw = false;
        try {
            adapter.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("visitEnd() did not throw IllegalStateException on second call");
        }
    }
}
