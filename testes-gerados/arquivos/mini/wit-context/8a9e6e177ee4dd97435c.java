/*
 * @test
 * @summary Test TraceModuleVisitor.visitEnd() method behavior
 * @run main TraceModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceModuleVisitor;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TraceModuleVisitorWitupTest {

    static class TestPrinter extends Printer {
        boolean visitModuleEndCalled = false;

        public TestPrinter() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitModuleEnd() {
            visitModuleEndCalled = true;
        }
    }

    static class TestModuleVisitor extends ModuleVisitor {
        boolean visitEndCalled = false;

        public TestModuleVisitor() {
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

        // Before visitEnd call
        if (printer.visitModuleEndCalled) {
            throw new RuntimeException("visitModuleEnd should not be called before visitEnd");
        }
        if (mv.visitEndCalled) {
            throw new RuntimeException("super.visitEnd should not be called before visitEnd");
        }

        // Call visitEnd
        tmv.visitEnd();

        // After visitEnd call, verify that visitModuleEnd and super.visitEnd were called
        if (!printer.visitModuleEndCalled) {
            throw new RuntimeException("visitModuleEnd was not called by visitEnd");
        }
        if (!mv.visitEndCalled) {
            throw new RuntimeException("super.visitEnd was not called by visitEnd");
        }
    }
}
