/* @test    @summary Test for visitProvide method in Printer class
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitProvideThrowsRuntimeException();
    }

    public static void testVisitProvideThrowsRuntimeException() {
        Printer printer = new Printer() {
            @Override
            public void visitProvide(String service, String... providers) {
                throw new RuntimeException("Must be overriden");
            }
        };
        boolean threwException = false;
        try {
            printer.visitProvide("testService", "provider1", "provider2");
        } catch (RuntimeException e) {
            threwException = true;
            assert "Must be overriden".equals(e.getMessage()) : "Unexpected exception message";
        }
        if (!threwException) {
            throw new AssertionError("Expected RuntimeException was not thrown");
        }
    }
}
