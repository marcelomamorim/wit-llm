/* @test    @summary Test for visitExport method in Printer class    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitExportThrowsRuntimeException();
    }

    public static void testVisitExportThrowsRuntimeException() {
        Printer printer = new Printer(0); // Using protected constructor, assuming default api=0
        try {
            printer.visitExport("com/example", 0, new String[] {"module1"});
            System.out.println("Test failed: RuntimeException was not thrown.");
        } catch (RuntimeException e) {
            if ("Must be overriden".equals(e.getMessage())) {
                System.out.println("Test passed: RuntimeException thrown as expected.");
            } else {
                System.out.println("Test failed: Unexpected RuntimeException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
