/*
 * @test
 * @summary Test for Collections.sort(Comparator<? super E> c) method.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Comparator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSortThrowsUnsupportedOperationException();
    }

    private static void testSortThrowsUnsupportedOperationException() {
        // Cria uma lista imutável para garantir que a operação de sort dispare UnsupportedOperationException
        java.util.List<Integer> list = java.util.Collections.unmodifiableList(java.util.Arrays.asList(3, 1, 2));
        try {
            Collections.sort(list, Comparator.naturalOrder());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        }
    }
}
