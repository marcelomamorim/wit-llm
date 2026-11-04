/*
 * @test
 * @summary Test TraceModuleVisitor.visitEnd() calls Printer.visitModuleEnd() and super.visitEnd()
 * @run main TraceModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceModuleVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TraceModuleVisitorWitupTest {

    static class TestPrinter extends Printer {
        boolean visitModuleEndCalled = false;

        TestPrinter() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitModuleEnd() {
            visitModuleEndCalled = true;
        }
    }

    static class TestModuleVisitor extends ModuleVisitor {
        boolean visitEndCalled = false;

        TestModuleVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        TestModuleVisitor mv = new TestModuleVisitor();
        TraceModuleVisitor tmv = new TraceModuleVisitor(mv, printer);

        tmv.visitEnd();

        if (!printer.visitModuleEndCalled) {
            throw new AssertionError("Printer.visitModuleEnd() was not called");
        }

        if (!mv.visitEndCalled) {
            throw new AssertionError("super.visitEnd() was not called");
        }
    }
}
