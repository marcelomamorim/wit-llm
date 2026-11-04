/* @test    @summary Test DelayQueue.offer(E e, long timeout, TimeUnit unit) method for basic functionality and regression protection    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testOfferReturnsTrueForValidElement();
        testOfferReturnsFalseForNullElement();
    }

    private static void testOfferReturnsTrueForValidElement() {
        DelayQueue<String> dq = new DelayQueue<>();
        String element = "test";
        boolean result = dq.offer(element, 1, TimeUnit.MILLISECONDS);
        if (!result) {
            throw new AssertionError("Expected offer to return true for valid element") ;
        }
        if (dq.isEmpty()) {
            throw new AssertionError("Queue should contain the offered element") ;
        }
        String polled = dq.poll();
        if (polled == null || !polled.equals(element)) {
            throw new AssertionError("Polled element does not match offered element") ;
        }
    }

    private static void testOfferReturnsFalseForNullElement() {
        DelayQueue<String> dq = new DelayQueue<>();
        try {
            dq.offer(null, 1, TimeUnit.MILLISECONDS);
            throw new AssertionError("Expected NullPointerException when offering null") ;
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
