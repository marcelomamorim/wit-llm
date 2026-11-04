/*
 * @test
 * @summary Testa o método Collections.removeIf com comportamento esperado.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfThrowsException();
    }

    public static void testRemoveIfThrowsException() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Predicate<String> predicate = s -> s.equals("b");
        try {
            boolean result = java.util.Collections.removeIf(list, predicate);
            // Como o método removeIf lança UnsupportedOperationException, não deve retornar normalmente.
            // Se chegar aqui, o teste falhou.
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido: exceção esperada foi lançada.
        }
    }
}
