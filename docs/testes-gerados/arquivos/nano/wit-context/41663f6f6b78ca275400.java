/*
 * @test
 * @summary Test for ConcurrentLinkedDeque.retainAll method, focusing on null input handling.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
    }

    public static void testRetainAllWithNull() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.retainAll(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for retainAll(null)");
        }
    }
}
