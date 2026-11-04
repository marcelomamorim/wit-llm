/*
 * @test
 * @summary Test visitModuleEnd method throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        PrinterWitupTest test = new PrinterWitupTest();
        test.testVisitModuleEndThrows();
    }

    static class Printer extends jdk.internal.org.objectweb.asm.util.Printer {
        protected Printer(int api) {
            super(api);
        }

        @Override
        public void visitModuleEnd() {
            throw new RuntimeException("Must be overriden");
        }
    }

    public void testVisitModuleEndThrows() {
        Printer printer = new Printer(0);
        try {
            printer.visitModuleEnd();
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Must be overriden")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
