/*
 * @test
 * @summary Testa o método removeIf de Collections para comportamento com Predicate nulo
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        List<String> list = List.of("a", "b", "c");
        try {
            // Como o método removeIf lança NullPointerException se o predicate for null,
            // esperamos que essa exceção seja lançada.
            Collections.removeIf(list, null);
            System.err.println("Falha: esperado NullPointerException ao passar predicate null.");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançada ao passar predicate null.");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
        }
    }

    private static void testRemoveIfWithValidPredicate() {
        List<String> list = new java.util.ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = Collections.removeIf(list, s -> s.equals("b"));
        if (result) {
            if (list.size() == 2 && !list.contains("b")) {
                System.out.println("Sucesso: removeIf removeu o elemento esperado.");
            } else {
                System.err.println("Falha: lista após removeIf não está no estado esperado.");
            }
        } else {
            System.err.println("Falha: removeIf retornou false, esperado true.");
        }
    }
}
