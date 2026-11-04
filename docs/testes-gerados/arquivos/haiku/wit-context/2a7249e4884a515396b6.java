/*
 * @test
 * @summary Verify that Printer.visitModule throws RuntimeException with the base message when not overridden
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
    }

    static void expectBaseException(Printer p, String name, int access, String version) {
        try {
            p.visitModule(name, access, version);
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        TestPrinter p = new TestPrinter();
        expectBaseException(p, "module.a", 0, "1.0");
        expectBaseException(p, null, 0, null);
        expectBaseException(p, "", -1, "");
    }
}
