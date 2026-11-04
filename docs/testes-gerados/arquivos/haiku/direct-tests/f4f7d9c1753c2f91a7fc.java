/*
 * @test
 * @summary Verify that the default Printer.visitUse throws RuntimeException with the "Must be overriden" message when a subclass does not override it
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import java.io.PrintWriter;

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer printer = new Printer(0) {
        };

        checkDefaultThrows(printer, "svc.Provider");
        checkDefaultThrows(printer, null);
        checkDefaultThrows(printer, "");
    }

    private static void checkDefaultThrows(Printer printer, String service) {
        RuntimeException thrown = null;
        try {
            printer.visitUse(service);
        } catch (RuntimeException e) {
            thrown = e;
        }
        if (thrown == null) {
            throw new AssertionError("Expected RuntimeException for service: " + service);
        }
        if (!"Must be overriden".equals(thrown.getMessage())) {
            throw new AssertionError("Unexpected message: " + thrown.getMessage());
        }
    }
}
