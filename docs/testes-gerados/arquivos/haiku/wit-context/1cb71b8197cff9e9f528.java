/*
 * @test
 * @summary Verify that Printer.visitPackage throws RuntimeException with the documented message when not overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer printer = new Printer(262144) {};
        boolean thrown = false;
        try {
            printer.visitPackage("jdk/internal/test");
        } catch (RuntimeException e) {
            thrown = true;
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("visitPackage did not throw RuntimeException");
        }
    }
}
