/*
 * @test
 * @summary Test for ImmutableCollections.put method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPutThrowsUOE();
    }

    public static void testPutThrowsUOE() {
        try {
            // Cria uma instância de uma coleção imutável, se possível, ou usa uma implementação que não permite put
            // Como o método put lança UnsupportedOperationException, apenas chamá-lo deve lançar a exceção
            ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<>();
            // Como o construtor é package-private, não podemos instanciá-lo diretamente; portanto, usamos uma subclasse anônima
            // ou uma implementação que herde de uma classe que não permite put
            // Como não há construtor público, vamos criar uma subclasse anônima apenas para invocar put
            ImmutableCollections.AbstractImmutableMap<String, String> testMap = new ImmutableCollections.AbstractImmutableMap<String, String>() {
                @Override
                public V put(K key, V value) {
                    return super.put(key, value);
                }
            };
            // Invoca put, que deve lançar UnsupportedOperationException
            testMap.put("key", "value");
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            // Qualquer outra exceção indica falha
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
