/*
 * @test
 * @summary Verifies that the default Printer.visitRequire throws RuntimeException with the expected message
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class TestPrinter extends Printer {
        TestPrinter() {
            super(327680);
        }

        public void print(PrintWriter printWriter) {
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        RuntimeException caught = null;
        try {
            printer.visitRequire("java.base", 0, "11");
        } catch (RuntimeException e) {
            caught = e;
        }
        if (caught == null) {
            throw new RuntimeException("Expected RuntimeException was not thrown");
        }
        if (!"Must be overriden".equals(caught.getMessage())) {
            throw new RuntimeException("Unexpected message: " + caught.getMessage());
        }
        if (caught.getClass() != RuntimeException.class) {
            throw new RuntimeException("Unexpected exception type: " + caught.getClass().getName());
        }
    }
}
