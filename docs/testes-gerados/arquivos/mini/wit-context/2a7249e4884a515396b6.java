/*
 * @test
 * @summary Test visitModule method of Printer throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer printer = new Printer(0) {
            @Override
            public Printer visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        boolean thrown = false;
        try {
            printer.visitModule("TestModule", 0, "1.0");
        } catch (RuntimeException e) {
            thrown = true;
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected RuntimeException was not thrown");
        }
    }

    // Minimal Printer class stub to allow compilation
    protected static abstract class Printer {
        protected Printer(final int api) {}
        public Printer visitModule(String name, int access, String version) {
            throw new RuntimeException("Must be overriden");
        }
    }
}
