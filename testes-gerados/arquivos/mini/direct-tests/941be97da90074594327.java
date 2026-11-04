/*
 * @test
 * @summary Test visitEnd method of CheckModuleAdapter for correct end flag and delegation
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
        DummyModuleVisitor dummy = new DummyModuleVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummy, false);

        // Before visitEnd, end should be false (not accessible directly, so test via behavior)
        // Call visitEnd
        adapter.visitEnd();

        // After visitEnd, dummy.visitEnd should have been called
        if (!dummy.visitEndCalled) {
            throw new AssertionError("super.visitEnd() was not called");
        }

        // Calling visitEnd again should throw IllegalStateException because end flag is set
        boolean threw = false;
        try {
            adapter.visitEnd();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("visitEnd did not throw IllegalStateException on second call");
        }
    }
}
