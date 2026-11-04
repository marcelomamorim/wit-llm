/*
 * @test
 * @summary Testa o método contains de LinkedBlockingDeque com entradas não nulas.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testContainsWithNonNullObject();
        testContainsWithNullObject();
    }

    private static void testContainsWithNonNullObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        boolean result = deque.contains("element");
        if (!result) {
            throw new AssertionError("Expected deque to contain 'element'");
        }
    }

    private static void testContainsWithNullObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        boolean result = deque.contains(null);
        if (result) {
            throw new AssertionError("Expected deque not to contain null");
        }
    }
}
