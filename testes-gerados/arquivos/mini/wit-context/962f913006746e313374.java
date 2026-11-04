/*
 * @test
 * @summary Test TraceMethodVisitor.visitParameter method behavior
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class TraceMethodVisitorWitupTest {

    static class DummyPrinter extends Printer {
        boolean visited = false;
        String lastName = null;
        int lastAccess = -1;

        public DummyPrinter() {
            super(0);
        }

        @Override
        public void visitParameter(String name, int access) {
            visited = true;
            lastName = name;
            lastAccess = access;
        }
    }

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visited = false;
        String lastName = null;
        int lastAccess = -1;

        public DummyMethodVisitor() {
            super(0);
        }

        @Override
        public void visitParameter(String name, int access) {
            visited = true;
            lastName = name;
            lastAccess = access;
        }
    }

    public static void main(String[] args) {
        // Test 1: visitParameter calls Printer.visitParameter and MethodVisitor.visitParameter
        DummyPrinter printer = new DummyPrinter();
        DummyMethodVisitor dummyMv = new DummyMethodVisitor();
        TraceMethodVisitor tmv = new TraceMethodVisitor(dummyMv, printer);

        String testName = "paramName";
        int testAccess = 123;

        tmv.visitParameter(testName, testAccess);

        if (!printer.visited) {
            throw new AssertionError("Printer.visitParameter was not called");
        }
        if (!testName.equals(printer.lastName) || testAccess != printer.lastAccess) {
            throw new AssertionError("Printer.visitParameter called with wrong arguments");
        }

        if (!dummyMv.visited) {
            throw new AssertionError("MethodVisitor.visitParameter was not called");
        }
        if (!testName.equals(dummyMv.lastName) || testAccess != dummyMv.lastAccess) {
            throw new AssertionError("MethodVisitor.visitParameter called with wrong arguments");
        }

        // Test 2: visitParameter with null name and zero access
        DummyPrinter printer2 = new DummyPrinter();
        DummyMethodVisitor dummyMv2 = new DummyMethodVisitor();
        TraceMethodVisitor tmv2 = new TraceMethodVisitor(dummyMv2, printer2);

        tmv2.visitParameter(null, 0);

        if (!printer2.visited) {
            throw new AssertionError("Printer.visitParameter was not called with null name");
        }
        if (printer2.lastName != null || printer2.lastAccess != 0) {
            throw new AssertionError("Printer.visitParameter called with wrong arguments for null name");
        }

        if (!dummyMv2.visited) {
            throw new AssertionError("MethodVisitor.visitParameter was not called with null name");
        }
        if (dummyMv2.lastName != null || dummyMv2.lastAccess != 0) {
            throw new AssertionError("MethodVisitor.visitParameter called with wrong arguments for null name");
        }
    }
}
