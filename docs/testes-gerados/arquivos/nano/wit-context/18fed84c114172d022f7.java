/*
 * @test
 * @summary Test for sun.nio.ch.Util.add method.
 * @run main UtilWitupTest
 */

import sun.nio.ch.Util;

public class UtilWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        try {
            // Invoca o método add com um objeto qualquer, esperando uma UnsupportedOperationException
            boolean result = Util.add("test");
            // Se não lançar exceção, o teste falha
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("UnsupportedOperationException correctly thrown.");
            System.exit(0);
        } catch (Throwable t) {
            // Qualquer outra exceção, o teste falha
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
