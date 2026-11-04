/*
 * @test
 * @summary Test for PriorityBlockingQueue.put(E e) method, focusing on exception paths.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPutWithNullElement();
        testPutWithValidElement();
    }

    private static void testPutWithNullElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.put(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testPutWithValidElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.put(42);
            if (queue.contains(42)) {
                System.out.println("PASS: Element successfully added");
            } else {
                System.out.println("FAIL: Element not found after put");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
