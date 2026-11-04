/*
 * @test
 * @summary Verify that the base Printer.visitRequire throws RuntimeException with message "Must be overriden" when not overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class TestPrinter extends Printer {
        TestPrinter() {
            super(0);
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        try {
            printer.visitRequire("java.base", 0, "11");
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
