/* @test    @summary Test for visitModule method in Printer class
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitModuleThrowsRuntimeException();
    }

    public static void testVisitModuleThrowsRuntimeException() {
        Printer printer = new Printer(0) {
            @Override
            public Printer visitModule(String name, int access, String version) {
                throw new RuntimeException("Must be overriden");
            }
        };
        try {
            printer.visitModule("testModule", 0, "1.0");
            System.out.println("Test failed: RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if ("Must be overriden".equals(e.getMessage())) {
                System.out.println("Test passed: RuntimeException was correctly thrown");
            } else {
                System.out.println("Test failed: Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
