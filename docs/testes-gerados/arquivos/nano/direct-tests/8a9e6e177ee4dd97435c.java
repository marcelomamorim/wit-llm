/*
 * @test
 * @summary Test visitEnd method of TraceModuleVisitor for correct invocation
 * @run main TraceModuleVisitorVisitEndTest
 */

import jdk.internal.org.objectweb.asm.util.TraceModuleVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class TraceModuleVisitorVisitEndTest {

    static class DummyPrinter {
        boolean visitedModuleEnd = false;
        void visitModuleEnd() {
            visitedModuleEnd = true;
        }
    }

    static class DummyModuleVisitor extends ModuleVisitor {
        boolean visitEndCalled = false;
        public DummyModuleVisitor() {
            super(0, null);
        }
        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        DummyPrinter printer = new DummyPrinter();
        DummyModuleVisitor dummyMV = new DummyModuleVisitor();
        TraceModuleVisitor tmv = new TraceModuleVisitor(dummyMV, printer);
        tmv.visitEnd();
        assert printer.visitedModuleEnd : "visitModuleEnd was not called";
        assert dummyMV.visitEndCalled : "super.visitEnd was not called";
    }
}
