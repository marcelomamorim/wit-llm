/*
 * @test
 * @summary Test TraceMethodVisitor.visitParameter method behavior
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TraceMethodVisitorWitupTest {

    static class TestPrinter extends Printer {
        String lastName;
        int lastAccess;

        public TestPrinter() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitParameter(String name, int access) {
            this.lastName = name;
            this.lastAccess = access;
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        TraceMethodVisitor tmv = new TraceMethodVisitor(printer);

        // Test with normal parameter name and access
        tmv.visitParameter("param1", 1);
        if (!"param1".equals(printer.lastName)) {
            throw new AssertionError("Expected name 'param1', got: " + printer.lastName);
        }
        if (printer.lastAccess != 1) {
            throw new AssertionError("Expected access 1, got: " + printer.lastAccess);
        }

        // Test with null name and zero access
        tmv.visitParameter(null, 0);
        if (printer.lastName != null) {
            throw new AssertionError("Expected name null, got: " + printer.lastName);
        }
        if (printer.lastAccess != 0) {
            throw new AssertionError("Expected access 0, got: " + printer.lastAccess);
        }

        // Test with empty string name and negative access
        tmv.visitParameter("", -1);
        if (!"".equals(printer.lastName)) {
            throw new AssertionError("Expected empty name, got: " + printer.lastName);
        }
        if (printer.lastAccess != -1) {
            throw new AssertionError("Expected access -1, got: " + printer.lastAccess);
        }

        // Test that super.visitParameter does not throw (no direct observable effect)
        // We rely on no exception thrown as success

        System.out.println("All tests passed.");
    }
}
