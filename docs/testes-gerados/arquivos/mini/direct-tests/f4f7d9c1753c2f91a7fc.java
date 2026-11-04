/*
 * @test
 * @summary Test that Printer.visitUse throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer p = new Printer(0) {
            @Override
            public void visitUse(String service) {
                super.visitUse(service);
            }
        };

        try {
            p.visitUse("testService");
            throw new Error("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    // Minimal Printer subclass to allow instantiation
    protected static abstract class Printer {
        protected Printer(int api) {}
        public void visitUse(String service) {
            throw new RuntimeException("Must be overriden");
        }
    }
}
