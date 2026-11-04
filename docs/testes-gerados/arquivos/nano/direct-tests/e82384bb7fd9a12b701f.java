/*
 * @test
 * @summary Test offer method of LinkedTransferQueue for basic functionality
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithElement();
    }

    static void testOfferWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        boolean result = false;
        try {
            result = queue.offer(null);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException as expected when offering null");
            return;
        }
        if (result) {
            System.out.println("offer(null) returned true");
        } else {
            System.out.println("offer(null) returned false");
        }
    }

    static void testOfferWithElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = queue.offer("test");
        if (result && queue.peek().equals("test")) {
            System.out.println("offer("test") succeeded and element is at head");
        } else {
            System.out.println("offer("test") failed or element not at head");
        }
    }
}
