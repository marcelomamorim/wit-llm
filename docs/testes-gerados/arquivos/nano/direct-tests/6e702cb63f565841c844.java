/*
 * @test    @summary Test offer method with timeout in LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithTimeoutSuccess();
        testOfferWithTimeoutNullElement();
    }

    static void testOfferWithTimeoutSuccess() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = queue.offer("element", 1, TimeUnit.MILLISECONDS);
        assert result : "offer should return true";
        assert queue.peek().equals("element") : "Element should be in queue";
    }

    static void testOfferWithTimeoutNullElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.offer(null, 1, TimeUnit.MILLISECONDS);
            assert false : "Null element should throw NullPointerException";
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
