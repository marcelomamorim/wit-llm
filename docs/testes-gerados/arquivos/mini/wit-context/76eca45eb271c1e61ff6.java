/*
 * @test
 * @summary Test that Printer.visitRequire throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        PrinterWitupTest test = new PrinterWitupTest();
        test.testVisitRequireThrows();
    }

    static class Printer extends jdk.internal.org.objectweb.asm.util.Printer {
        protected Printer(int api) {
            super(api);
        }

        @Override
        public void visitRequire(String module, int access, String version) {
            throw new RuntimeException("Must be overriden");
        }
    }

    public void testVisitRequireThrows() {
        Printer printer = new Printer(0);
        boolean thrown = false;
        try {
            printer.visitRequire("moduleName", 0, "1.0");
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
}
