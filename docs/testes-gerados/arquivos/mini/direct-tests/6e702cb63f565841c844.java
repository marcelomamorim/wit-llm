/*
 * @test
 * @summary Test LinkedTransferQueue.offer(E,long,TimeUnit) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test offer with normal element and zero timeout
        boolean result1 = queue.offer("test1", 0L, TimeUnit.MILLISECONDS);
        if (!result1) throw new AssertionError("offer returned false for normal element");

        // Test offer with normal element and positive timeout
        boolean result2 = queue.offer("test2", 100L, TimeUnit.MILLISECONDS);
        if (!result2) throw new AssertionError("offer returned false for positive timeout");

        // Test offer with normal element and large timeout
        boolean result3 = queue.offer("test3", Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        if (!result3) throw new AssertionError("offer returned false for large timeout");

        // Test offer with null element - should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.offer(null, 0L, TimeUnit.MILLISECONDS);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) throw new AssertionError("offer did not throw NullPointerException for null element");

        // Test offer with null TimeUnit - should throw NullPointerException
        boolean npeThrown2 = false;
        try {
            queue.offer("test4", 0L, null);
        } catch (NullPointerException ex) {
            npeThrown2 = true;
        }
        if (!npeThrown2) throw new AssertionError("offer did not throw NullPointerException for null TimeUnit");

        // Verify queue contains the offered elements (except null)
        if (!queue.contains("test1")) throw new AssertionError("Queue missing element 'test1'");
        if (!queue.contains("test2")) throw new AssertionError("Queue missing element 'test2'");
        if (!queue.contains("test3")) throw new AssertionError("Queue missing element 'test3'");
        if (queue.contains("test4")) throw new AssertionError("Queue should not contain 'test4'");

        System.out.println("All tests passed.");
    }
}
