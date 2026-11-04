/*
 * @test
 * @summary Testa o método removeFirst() de LinkedBlockingDeque para comportamento esperado.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstWithElements();
        testRemoveFirstEmptyDeque();
    }

    static void testRemoveFirstWithElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        Integer result = deque.removeFirst();
        if (result == null || result != 1) {
            throw new AssertionError("Expected removeFirst() to return 1, but got: " + result);
        }
        if (deque.size() != 1) {
            throw new AssertionError("Deque size should be 1 after removal, but is: " + deque.size());
        }
    }

    static void testRemoveFirstEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when removeFirst() called on empty deque");
        }
    }
}
