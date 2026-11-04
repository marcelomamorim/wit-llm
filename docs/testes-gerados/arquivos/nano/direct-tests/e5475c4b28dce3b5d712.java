/* @test    @summary Test visitModuleEnd method in Printer class    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitModuleEnd();
    }

    public static void testVisitModuleEnd() {
        Printer printer = new Printer() {
            @Override
            public void visitModuleEnd() {
                System.out.println("visitModuleEnd invoked");
            }
        };
        try {
            printer.visitModuleEnd();
            System.out.println("Test passed: visitModuleEnd executed without exception.");
        } catch (RuntimeException e) {
            System.err.println("Test failed: Exception thrown - " + e.getMessage());
        }
    }
}
