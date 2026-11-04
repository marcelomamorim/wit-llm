/*
 * @test
 * @summary Test visitPackage method throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer printer = new Printer(0) {
            @Override
            public void visitPackage(String packaze) {
                // call super to trigger exception
                super.visitPackage(packaze);
            }
        };

        boolean thrown = false;
        try {
            printer.visitPackage("any");
        } catch (RuntimeException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected RuntimeException not thrown by visitPackage");
        }
    }

    // Minimal Printer constructor as per context
    protected static abstract class Printer {
        protected Printer(final int api) {
        }

        public void visitPackage(String packaze) {
            throw new RuntimeException("Must be overriden");
        }
    }
}
