/*
 * @test
 * @summary Verifies that the default Printer.visitModule throws RuntimeException with the "Must be overriden" message
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testDefaultVisitModuleThrows();
        testDefaultVisitModuleThrowsWithNullArguments();
        testOverriddenVisitModuleReturnsValue();
    }

    static void testDefaultVisitModuleThrows() {
        Printer p = new Printer(Opcodes.ASM5) {};
        try {
            p.visitModule("m", 0, "1.0");
            throw new AssertionError("Expected RuntimeException");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testDefaultVisitModuleThrowsWithNullArguments() {
        Printer p = new Printer(Opcodes.ASM5) {};
        try {
            p.visitModule(null, 0, null);
            throw new AssertionError("Expected RuntimeException");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testOverriddenVisitModuleReturnsValue() {
        final Printer expected = new Printer(Opcodes.ASM5) {};
        Printer p = new Printer(Opcodes.ASM5) {
            @Override
            public Printer visitModule(String name, int access, String version) {
                return expected;
            }
        };
        if (p.visitModule("m", 0, "1.0") != expected) {
            throw new AssertionError("Overridden visitModule did not return expected value");
        }
    }
}
