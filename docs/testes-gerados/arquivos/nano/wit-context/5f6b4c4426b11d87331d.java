/*
 * @test
 * @summary Testa o método previous() de CopyOnWriteArrayList para comportamento esperado.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsWhenCursorIsZero();
        testPreviousReturnsElementWhenCursorPositive();
    }

    static void testPreviousThrowsWhenCursorIsZero() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        // Cria um iterador manualmente para manipular cursor
        CopyOnWriteArrayList<E>.COWIterator iterator = list.new COWIterator();
        // Força cursor para 0
        iterator.cursor = 0;
        try {
            iterator.previous();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // esperado
        }
    }

    static void testPreviousReturnsElementWhenCursorPositive() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        // Cria um iterador e move cursor para 2
        CopyOnWriteArrayList<E>.COWIterator iterator = list.new COWIterator();
        iterator.cursor = 2;
        String element = iterator.previous();
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b', got: " + element);
        }
        if (iterator.cursor != 1) {
            throw new AssertionError("Cursor not decremented correctly");
        }
    }
}
