/*
 * @test
 * @summary Test that Printer.visitModuleEnd throws RuntimeException as expected
 * @run main PrinterWitupTest
 */
public class PrinterWitupTest {
    public static void main(String[] args) {
        jdk.internal.org.objectweb.asm.util.Printer printer = new jdk.internal.org.objectweb.asm.util.Printer(0) {
            @Override
            public void visitModuleEnd() {
                // call super to trigger exception
                super.visitModuleEnd();
            }
        };
        try {
            printer.visitModuleEnd();
            throw new Error("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
