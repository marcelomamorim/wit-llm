/*
 * @test
 * @summary Testa o método replace de Collections que lança UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsException();
    }

    public static void testReplaceThrowsException() {
        try {
            boolean result = Collections.replace(null, null, null);
            // Como o método lança exceção imediatamente, não deve chegar aqui
            System.err.println("Erro: esperado UnsupportedOperationException, mas método retornou: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Passou: UnsupportedOperationException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: esperado UnsupportedOperationException, mas ocorreu: " + t);
        }
    }
}
