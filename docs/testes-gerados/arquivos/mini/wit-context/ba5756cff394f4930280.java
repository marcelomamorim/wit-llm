/*
 * @test
 * @summary Test DelayQueue.offer(E e, long timeout, TimeUnit unit) method
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {
    public static void main(String[] args) {
        DelayQueue<Object> dq = new DelayQueue<>();
        Object element = new Object();

        // The offer(E e, long timeout, TimeUnit unit) method delegates to offer(E e)
        // which is unsupported and throws UnsupportedOperationException.
        boolean thrown = false;
        try {
            dq.offer(element, 1L, TimeUnit.SECONDS);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
