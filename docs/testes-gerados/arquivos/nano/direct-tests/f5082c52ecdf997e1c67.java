/*
 * @test    @summary Test the remove() method of LinkedBlockingQueue for correct exception and behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyQueue();
        testRemoveAfterAddingElement();
    }

    static void testRemoveOnEmptyQueue() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        try {
            queue.remove();
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testRemoveAfterAddingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("element");
        try {
            queue.remove();
            if (queue.isEmpty()) {
                System.out.println("PASS: Element removed successfully");
            } else {
                System.out.println("FAIL: Queue not empty after remove");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
