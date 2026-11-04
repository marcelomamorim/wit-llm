/*
 * @test
 * @summary Test removeAll with null collection to trigger NullPointerException
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNull();
    }

    public static void testRemoveAllWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.removeAll(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
