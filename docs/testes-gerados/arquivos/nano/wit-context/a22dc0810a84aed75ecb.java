/* @test    @summary Test for ArrayDeque.forEachRemaining with null Consumer    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
    }

    public static void testForEachRemainingNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        try {
            deque.forEachRemaining(null);
            // Se nenhuma exceção for lançada, o teste falha
            throw new AssertionError("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Teste bem-sucedido: exceção esperada
        }
    }
}
