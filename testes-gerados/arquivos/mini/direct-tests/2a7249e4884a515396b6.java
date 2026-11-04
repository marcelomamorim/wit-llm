/*
 * @test
 * @summary Test that Printer.visitModule throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer p = new Printer(0) {
            @Override
            public Printer visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        boolean thrown = false;
        try {
            p.visitModule("TestModule", 1, "1.0");
        } catch (RuntimeException e) {
            if ("Must be overriden".equals(e.getMessage())) {
                thrown = true;
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected RuntimeException was not thrown");
        }
    }

    // Minimal Printer constructor to allow instantiation
    protected static abstract class Printer {
        protected Printer(int api) {}
        public Printer visitModule(String name, int access, String version) {
            throw new RuntimeException("Must be overriden");
        }
    }
}
