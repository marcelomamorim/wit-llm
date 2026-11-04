/* @test    @summary Test for getDecimalExponent method in FloatingDecimal class    @run main FloatingDecimalWitupTest */

import java.io.StringWriter;
import java.io.Writer;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testGetDecimalExponentThrowsException();
    }

    private static void testGetDecimalExponentThrowsException() {
        jdk.internal.math.FloatingDecimal fd = new jdk.internal.math.FloatingDecimal();
        try {
            int result = fd.getDecimalExponent();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("PASS: IllegalArgumentException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
