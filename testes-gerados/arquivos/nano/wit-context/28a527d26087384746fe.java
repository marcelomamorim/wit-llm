/*
 * @test
 * @summary Testa o método forEachRemaining de ArrayDeque para comportamento esperado com entrada válida e nula.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        deque.forEachRemaining(consumer);
        assert sb.toString().equals("123") : "Expected concatenation of elements";
    }

    static void testForEachRemainingWithNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        try {
            deque.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
