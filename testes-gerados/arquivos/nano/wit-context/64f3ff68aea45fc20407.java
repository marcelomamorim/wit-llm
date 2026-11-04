/*
 * @test
 * @summary Testa o método Collections.removeIf com comportamento esperado de exceção
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfThrowsUnsupportedOperationException();
    }

    public static void testRemoveIfThrowsUnsupportedOperationException() {
        try {
            // Cria uma instância de uma coleção imutável, por exemplo, uma singleton
            java.util.Set<Integer> singletonSet = Collections.singleton(1);
            // Tenta remover elementos usando removeIf, que deve lançar UnsupportedOperationException
            boolean result = singletonSet.removeIf(new Predicate<Integer>() {
                @Override
                public boolean test(Integer t) {
                    return true;
                }
            });
            // Se não lançar exceção, o teste falha
            System.err.println("Falha: Esperado UnsupportedOperationException, mas não ocorreu.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("Sucesso: UnsupportedOperationException lançada como esperado.");
        } catch (Exception e) {
            // Qualquer outra exceção indica falha
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }
}
