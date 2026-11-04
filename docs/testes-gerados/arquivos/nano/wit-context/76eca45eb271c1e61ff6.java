/* @test    @summary Test for visitRequire method in Printer class    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitRequireThrowsRuntimeException();
    }

    public static void testVisitRequireThrowsRuntimeException() {
        Printer printer = new Printer();
        try {
            printer.visitRequire("testModule", 0, "1.0");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected RuntimeException was not thrown.");
        } catch (RuntimeException e) {
            // Verifica se a mensagem contém 'Must be overriden'
            if (e.getMessage() == null || !e.getMessage().contains("Must be overriden")) {
                throw new AssertionError("RuntimeException message mismatch.");
            }
        }
    }
}
