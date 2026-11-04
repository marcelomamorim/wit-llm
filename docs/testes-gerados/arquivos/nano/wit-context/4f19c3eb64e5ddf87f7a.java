/*
 * @test
 * @summary Testa o método pollFirst() de LinkedBlockingDeque para comportamento padrão.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollFirstEmptyDeque();
        testPollFirstWithElements();
    }

    private static void testPollFirstEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        assert deque.pollFirst() == null : "Expected null when polling from empty deque";
    }

    private static void testPollFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("A");
        deque.add("B");
        String first = deque.pollFirst();
        assert "A".equals(first) : "Expected first element to be 'A'";
        assert deque.size() == 1 : "Deque should have one element after poll";
        assert "B".equals(deque.peekFirst()) : "Next element should be 'B'";
    }
}
