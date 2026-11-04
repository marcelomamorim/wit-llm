/*
 * @test
 * @summary Test visitExport method throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        PrinterWitupTest test = new PrinterWitupTest();
        test.testVisitExportThrows();
    }

    // Minimal subclass to instantiate abstract Printer
    static class TestPrinter extends jdk.internal.org.objectweb.asm.util.Printer {
        protected TestPrinter() {
            super(0);
        }

        @Override
        public void visitExport(String packaze, int access, String... modules) {
            super.visitExport(packaze, access, modules);
        }
    }

    public void testVisitExportThrows() {
        TestPrinter printer = new TestPrinter();
        try {
            printer.visitExport("some.package", 0, "module1", "module2");
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
