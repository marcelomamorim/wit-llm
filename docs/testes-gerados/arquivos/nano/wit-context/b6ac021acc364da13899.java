/* @test    @summary Test for method decimalDigitsExact in class jdk.internal.math.FloatingDecimal    @run main FloatingDecimalWitupTest */

import java.io.*;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testDecimalDigitsExactThrowsException();
    }

    private static void testDecimalDigitsExactThrowsException() {
        try {
            // Invoca o método que, na implementação atual, lança IllegalArgumentException
            boolean result = jdk.internal.math.FloatingDecimal.decimalDigitsExact();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Exceção esperada, o teste passa
            System.out.println("PASS: IllegalArgumentException was correctly thrown.");
        } catch (Throwable t) {
            // Qualquer outra exceção causa falha
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
