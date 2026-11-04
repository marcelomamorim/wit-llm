/*
 * @test
 * @summary Verify that Printer.visitProvide throws the documented base-class RuntimeException when not overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    private static final int API = 262144;

    public static void main(String[] args) {
        Printer printer = new Printer(API) {};

        expectBaseException(printer, "svc", "impl1", "impl2");
        expectBaseException(printer, "svc");
        expectBaseException(printer, "svc", new String[0]);
        expectBaseException(printer, null, "impl1");
    }

    private static void expectBaseException(Printer printer, String service, String... providers) {
        try {
            printer.visitProvide(service, providers);
        } catch (RuntimeException e) {
            if (e.getClass() != RuntimeException.class) {
                throw new AssertionError("Unexpected exception type: " + e.getClass().getName());
            }
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("visitProvide did not throw RuntimeException");
    }
}
