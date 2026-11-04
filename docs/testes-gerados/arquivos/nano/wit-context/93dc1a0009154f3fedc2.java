/* @test    @summary Test for ImmutableCollections.sort to verify it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.Comparator;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testSortThrowsUOE();
    }

    public static void testSortThrowsUOE() {
        List<Integer> list = List.of(3, 1, 2);
        try {
            // Invoca o método sort, que deve lançar UnsupportedOperationException
            list.sort(Comparator.naturalOrder());
            // Se não lançar, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido: exceção foi lançada
        }
    }
}
