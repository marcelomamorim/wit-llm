/* @test    @summary Test for visitModuleEnd method in Printer class    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitModuleEnd();
    }

    public static void testVisitModuleEnd() {
        Printer printer = new Printer();
        try {
            printer.visitModuleEnd();
            // Se não lançar exceção, o teste passa, pois o método não tem comportamento definido.
        } catch (RuntimeException e) {
            // Verifica se a exceção é a esperada.
            if ("Must be overriden".equals(e.getMessage())) {
                System.out.println("testVisitModuleEnd passed");
            } else {
                System.out.println("testVisitModuleEnd failed: unexpected message" );
            }
        } catch (Exception e) {
            System.out.println("testVisitModuleEnd failed: unexpected exception" );
        }
    }
}
