/*
 * @test
 * @summary Testa o método removeLast() de LinkedBlockingDeque para comportamento esperado.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastWithElements();
        testRemoveLastEmptyDeque();
    }

    static void testRemoveLastWithElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        Integer last = deque.removeLast();
        if (last == null || last != 2) {
            throw new AssertionError("Expected last element to be 2, but got: " + last);
        }
        if (deque.size() != 1) {
            throw new AssertionError("Expected deque size to be 1 after removal, but got: " + deque.size());
        }
    }

    static void testRemoveLastEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown when removing from empty deque");
        }
    }
}
