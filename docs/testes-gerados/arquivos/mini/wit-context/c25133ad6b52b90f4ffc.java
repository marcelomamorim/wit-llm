/*
 * @test
 * @summary Test that Printer.visitProvide throws RuntimeException as expected
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        PrinterWitupTest test = new PrinterWitupTest();
        test.testVisitProvideThrows();
    }

    static class Printer {
        public void visitProvide(String service, String... providers) {
            throw new RuntimeException("Must be overriden");
        }
    }

    public void testVisitProvideThrows() {
        Printer printer = new Printer();
        try {
            printer.visitProvide("serviceName", "provider1", "provider2");
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            // Expected exception
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
