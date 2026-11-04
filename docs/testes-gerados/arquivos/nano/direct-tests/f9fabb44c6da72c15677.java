/*
 * @test    @summary Test the contains method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
        testContainsWithNull();
    }

    static void testContainsWithExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String element = "test";
        queue.offer(element);
        boolean result = queue.contains(element);
        if (!result) {
            throw new AssertionError("Expected queue to contain the element") ;
        }
    }

    static void testContainsWithNonExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        boolean result = queue.contains("b");
        if (result) {
            throw new AssertionError("Expected queue not to contain the element") ;
        }
    }

    static void testContainsWithNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        boolean result = queue.contains(null);
        if (result) {
            throw new AssertionError("Expected queue not to contain null") ;
        }
    }
}
