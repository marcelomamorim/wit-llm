/*
 * @test
 * @summary Testa o método peek() de PriorityBlockingQueue para comportamento padrão.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekEmptyQueue();
        testPeekWithElements();
    }

    static void testPeekEmptyQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Integer result = pq.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, got: " + result);
        }
    }

    static void testPeekWithElements() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(5);
        pq.add(20);
        Integer peeked = pq.peek();
        if (peeked == null) {
            throw new AssertionError("Peek returned null on non-empty queue");
        }
        if (peeked != 5) {
            throw new AssertionError("Expected peek to return 5, got: " + peeked);
        }
        // Confirm que o elemento no topo permanece após peek
        Integer first = pq.poll();
        if (first == null || first != 5) {
            throw new AssertionError("Poll did not return the expected top element: 5");
        }
    }
}
