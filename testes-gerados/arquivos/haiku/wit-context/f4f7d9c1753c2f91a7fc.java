/*
 * @test
 * @summary Verify that Printer.visitUse(String) throws RuntimeException with the documented message when not overridden
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

    static void checkThrows(String service) {
        TestPrinter printer = new TestPrinter();
        RuntimeException thrown = null;
        try {
            printer.visitUse(service);
        } catch (RuntimeException e) {
            thrown = e;
        }
        if (thrown == null) {
            throw new AssertionError("Expected RuntimeException for service: " + service);
        }
        if (thrown.getClass() != RuntimeException.class) {
            throw new AssertionError("Unexpected exception class: " + thrown.getClass());
        }
        if (!"Must be overriden".equals(thrown.getMessage())) {
            throw new AssertionError("Unexpected message: " + thrown.getMessage());
        }
    }

    public static void main(String[] args) {
        checkThrows("java.sql.Driver");
        checkThrows("");
        checkThrows(null);
    }
}
