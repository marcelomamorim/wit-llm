/* @test    @summary Test DelayQueue.offer(E e, long timeout, TimeUnit unit) with normal element and timeout.    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithElementAndTimeout();
    }

    private static void testOfferWithElementAndTimeout() {
        DelayQueue<String> dq = new DelayQueue<>();
        String element = "test-element";
        boolean result = dq.offer(element, 1, TimeUnit.MILLISECONDS);
        if (!result) {
            throw new AssertionError("Expected offer to return true for valid element.");
        }
        // Verify that the element is in the queue
        boolean found = false;
        for (String e : dq) {
            if (e.equals(element)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Element not found in DelayQueue after offer.");
        }
        // Verify that size is at least 1
        if (dq.size() < 1) {
            throw new AssertionError("DelayQueue size should be at least 1 after offer.");
        }
        // Verify that offer does not throw exception with valid parameters
        try {
            boolean res = dq.offer("another-element", 10, TimeUnit.MILLISECONDS);
            if (!res) {
                throw new AssertionError("Expected offer to return true for second element.");
            }
        } catch (Exception e) {
            throw new AssertionError("offer threw unexpected exception: " + e);
        }
        // Test that offer with null element throws NullPointerException
        try {
            dq.offer(null, 1, TimeUnit.MILLISECONDS);
            throw new AssertionError("Expected NullPointerException when offering null.");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when offering null: " + e);
        }
        // Test that offer with negative timeout still returns true (as per current implementation)
        try {
            boolean resNeg = dq.offer("negative-timeout", -1, TimeUnit.MILLISECONDS);
            if (!resNeg) {
                throw new AssertionError("Expected offer to return true even with negative timeout.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception with negative timeout: " + e);
        }
    }
}
