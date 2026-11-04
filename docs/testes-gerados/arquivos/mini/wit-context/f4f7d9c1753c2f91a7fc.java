/*
 * @test
 * @summary Test visitUse method throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        PrinterWitupTest test = new PrinterWitupTest();
        test.testVisitUseThrows();
    }

    static class Printer extends jdk.internal.org.objectweb.asm.util.Printer {
        protected Printer(int api) {
            super(api);
        }

        @Override
        public void visitUse(String service) {
            super.visitUse(service);
        }
    }

    public void testVisitUseThrows() {
        Printer printer = new Printer(0);
        try {
            printer.visitUse("anyService");
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Must be overriden")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
