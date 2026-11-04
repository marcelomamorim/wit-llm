/*
 * @test
 * @summary Testa o método remove(int index) de Collections que lança UnsupportedOperationException.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove(0);
            System.err.println("Falha: esperado UnsupportedOperationException não foi lançada.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido
            System.out.println("Teste passou: UnsupportedOperationException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
            System.exit(1);
        }
    }
}
