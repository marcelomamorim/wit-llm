/*
 * @test
 * @summary Testa o método getFirst() de LinkedBlockingDeque para comportamento esperado.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testGetFirstWithElement();
        testGetFirstEmptyDeque();
    }

    static void testGetFirstWithElement() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(10);
        Integer result = deque.getFirst();
        if (result == null || result != 10) {
            throw new AssertionError("Expected first element to be 10, but got " + result);
        }
    }

    static void testGetFirstEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.getFirst();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
