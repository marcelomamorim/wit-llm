/*
 * @test
 * @summary Verifies that Printer.visitModuleEnd() throws RuntimeException with the base-class message when not overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class TestPrinter extends Printer {
        TestPrinter() {
            super(262144);
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        boolean thrown = false;
        try {
            printer.visitModuleEnd();
        } catch (RuntimeException e) {
            thrown = true;
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            if (e.getClass() != RuntimeException.class) {
                throw new RuntimeException("Unexpected exception type: " + e.getClass());
            }
        }
        if (!thrown) {
            throw new RuntimeException("visitModuleEnd() did not throw");
        }

        boolean secondThrown = false;
        try {
            printer.visitModuleEnd();
        } catch (RuntimeException e) {
            secondThrown = true;
        }
        if (!secondThrown) {
            throw new RuntimeException("visitModuleEnd() did not throw on second call");
        }
    }
}
