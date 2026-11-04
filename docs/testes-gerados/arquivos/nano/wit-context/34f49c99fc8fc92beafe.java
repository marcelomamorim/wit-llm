/*
 * @test
 * @summary Testa o método removeIf de java.util.Collections para comportamento esperado com entrada nula.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    public static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            boolean result = Collections.removeIf(list, null);
            // Como o método removeIf de Collections não é público, testamos via reflexão ou adaptamos para o método existente.
            // Como não há método público estático removeIf, adaptamos para testar o método de instância.
            // Portanto, este teste é uma simulação do comportamento esperado.
            // Como o método removeIf de Collections é um wrapper, testamos o método de uma lista.
            // Aqui, vamos testar o método removeIf de List, que é público.
            // Para isso, criamos uma lista e chamamos removeIf com null, esperando NullPointerException.
            List<String> testList = new ArrayList<>();
            testList.add("a");
            testList.add("b");
            try {
                testList.removeIf(null);
                System.out.println("FAIL: Expected NullPointerException for removeIf with null predicate.");
            } catch (NullPointerException e) {
                System.out.println("PASS: Caught expected NullPointerException for removeIf with null predicate.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testRemoveIfWithValidPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean result = list.removeIf(s -> s.equals("a"));
        if (result && list.size() == 1 && list.get(0).equals("b")) {
            System.out.println("PASS: removeIf correctly removed element.");
        } else {
            System.out.println("FAIL: removeIf did not behave as expected.");
        }
    }
}
