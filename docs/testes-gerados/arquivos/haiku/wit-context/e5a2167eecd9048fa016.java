/*
 * @test
 * @summary Verify that the base Printer.visitExport throws RuntimeException with message "Must be overriden" for various arguments
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    private static final class TestPrinter extends Printer {
        TestPrinter() {
            super(5);
        }
    }

    private static void expectMustBeOverriden(Printer p, String packaze, int access, String... modules) {
        try {
            p.visitExport(packaze, access, modules);
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new AssertionError("Expected RuntimeException was not thrown");
    }

    public static void main(String[] args) {
        Printer p = new TestPrinter();
        expectMustBeOverriden(p, "pkg/a", 0);
        expectMustBeOverriden(p, "pkg/a", 0, "mod1");
        expectMustBeOverriden(p, "pkg/a", 0, "mod1", "mod2");
        expectMustBeOverriden(p, null, 0);
        expectMustBeOverriden(p, "", -1, (String[]) null);
    }
}
