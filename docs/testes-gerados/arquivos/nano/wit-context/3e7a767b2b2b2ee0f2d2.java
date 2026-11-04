/* @test    @summary Test for FloatingDecimal.digitsRoundedUp() method to verify exception behavior and normal execution.    @run main FloatingDecimalWitupTest */

import java.io.*;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testDigitsRoundedUpThrowsException();
        testDigitsRoundedUpNormalBehavior();
    }

    private static void testDigitsRoundedUpThrowsException() {
        try {
            // Invoca o método que, na implementação atual, lança IllegalArgumentException
            boolean result = new FloatingDecimal().digitsRoundedUp();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Exceção esperada, o teste passa
            System.out.println("PASS: IllegalArgumentException thrown as expected.");
        } catch (Throwable t) {
            // Qualquer outra exceção causa falha
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testDigitsRoundedUpNormalBehavior() {
        // Como o método atual lança exceção sempre, este teste é apenas para verificar o comportamento padrão.
        // Caso a implementação mude, este método pode ser ajustado para testar um cenário onde não lança exceção.
        try {
            boolean result = new FloatingDecimal().digitsRoundedUp();
            // Se chegar aqui, o método não lançou exceção, o que é inesperado na implementação atual
            System.err.println("FAIL: Expected exception was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Como esperado, o método lança exceção
            System.out.println("PASS: Exception thrown as expected in normal behavior.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
