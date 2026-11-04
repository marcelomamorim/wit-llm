/*
 * @test
 * @summary Test that Printer.visitPackage throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    static class TestPrinter extends jdk.internal.org.objectweb.asm.util.Printer {
        protected TestPrinter() {
            super(0);
        }
    }

    public static void main(String[] args) {
        TestPrinter printer = new TestPrinter();
        try {
            printer.visitPackage("any");
            throw new Error("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
