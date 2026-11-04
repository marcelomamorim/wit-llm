/* @test    @summary Test for visitUse method in Printer class to verify RuntimeException is thrown as expected    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitUseThrowsRuntimeException();
    }

    public static void testVisitUseThrowsRuntimeException() {
        Printer printer = new Printer(0); // Using protected constructor with api=0
        String service = "testService";
        try {
            printer.visitUse(service);
            System.out.println("Test failed: RuntimeException was not thrown.");
        } catch (RuntimeException e) {
            if ("Must be overriden".equals(e.getMessage())) {
                System.out.println("Test passed: RuntimeException thrown as expected.");
            } else {
                System.out.println("Test failed: Unexpected RuntimeException message.");
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
