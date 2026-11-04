/*
 * @test
 * @summary Testa o método put de java.util.Collections que lança UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutThrowsException();
    }

    public static void testPutThrowsException() {
        try {
            Collections.put("key", "value");
            System.err.println("Erro: esperado UnsupportedOperationException não foi lançada.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Teste passou, exceção esperada
            System.out.println("Sucesso: UnsupportedOperationException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
            System.exit(1);
        }
    }
}
