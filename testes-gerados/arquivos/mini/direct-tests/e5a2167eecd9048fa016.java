/*
 * @test
 * @summary Test that Printer.visitExport throws RuntimeException as it must be overridden
 * @run main PrinterWitupTest
 */

public class PrinterWitupTest {

    public static void main(String[] args) {
        Printer p = new Printer(0) {
            // no override of visitExport
        };
        try {
            p.visitExport("some.package", 0, "module1", "module2");
            throw new Error("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

// Minimal stub of Printer class to allow compilation
abstract class Printer {
    protected Printer(final int api) {}
    public void visitExport(String packaze, int access, String... modules) {
        throw new RuntimeException("Must be overriden");
    }
}
