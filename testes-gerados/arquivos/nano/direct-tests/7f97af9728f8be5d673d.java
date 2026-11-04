/* @test    @summary Test offer method of PriorityBlockingQueue with non-null element    @run main PriorityBlockingQueueOfferTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueOfferTest {

    public static void main(String[] args) {
        testOfferWithValidElement();
        testOfferWithNullElement();
    }

    static void testOfferWithValidElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean result = queue.offer(10);
        if (!result) {
            throw new AssertionError("offer should return true for non-null element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should contain the element after offer");
        }
        Integer head = queue.peek();
        if (head == null || head != 10) {
            throw new AssertionError("Head of queue should be 10");
        }
    }

    static void testOfferWithNullElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("offer(null) should throw NullPointerException");
        }
    }
}
