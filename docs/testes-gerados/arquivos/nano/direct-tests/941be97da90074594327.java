/* @test    @summary Test CheckModuleAdapter.visitEnd() method for correct behavior    @run main CheckModuleAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndSetsEndFlag();
        testVisitEndCallsSuperVisitEnd();
    }

    private static void testVisitEndSetsEndFlag() {
        class DummyModuleVisitor extends ModuleVisitor {
            boolean visitedEnd = false;
            public DummyModuleVisitor() {
                super(0, null);
            }
            @Override
            public void visitEnd() {
                visitedEnd = true;
            }
        }
        DummyModuleVisitor dummy = new DummyModuleVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummy, false);
        adapter.visitEnd();
        assert dummy.visitedEnd : "visitEnd() should call super.visitEnd()";
        assert adapter.end : "visitEnd() should set end to true";
    }

    private static void testVisitEndCallsSuperVisitEnd() {
        class SuperCallModuleVisitor extends ModuleVisitor {
            boolean superVisitedEnd = false;
            public SuperCallModuleVisitor() {
                super(0, null);
            }
            @Override
            public void visitEnd() {
                superVisitedEnd = true;
                super.visitEnd();
            }
        }
        SuperCallModuleVisitor superVisitor = new SuperCallModuleVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(superVisitor, false);
        adapter.visitEnd();
        assert superVisitor.superVisitedEnd : "visitEnd() should call super.visitEnd()";
        assert adapter.end : "visitEnd() should set end to true";
    }
}
