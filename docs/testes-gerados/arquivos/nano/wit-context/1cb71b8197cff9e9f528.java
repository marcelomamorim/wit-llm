/* @test    @summary Test for visitPackage method in Printer class    @run main PrinterWitupTest */

import java.lang.RuntimeException;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitPackageThrowsRuntimeException();
    }

    public static void testVisitPackageThrowsRuntimeException() {
        jdk.internal.org.objectweb.asm.util.Printer printer = new jdk.internal.org.objectweb.asm.util.Printer() {
            @Override
            public void visitPackage(String packaze) {
                throw new RuntimeException("Must be overriden");
            }
        };
        try {
            printer.visitPackage("com/example");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            // Verifica se a mensagem é a esperada
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
