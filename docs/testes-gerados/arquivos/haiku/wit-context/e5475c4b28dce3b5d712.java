/*
 * @test
 * @summary Verifies that the default Printer.visitModuleEnd() throws RuntimeException with the documented message
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */
import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class TestPrinter extends Printer {
        TestPrinter() {
            super(262144);
        }

        public void print(PrintWriter printWriter) {
        }
    }

    public static void main(String[] args) {
        Printer printer = new TestPrinter();
        try {
            printer.visitModuleEnd();
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("visitModuleEnd() did not throw RuntimeException");
    }
}
