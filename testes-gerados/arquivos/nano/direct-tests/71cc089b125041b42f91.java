/*
 * @test    @summary Test PriorityBlockingQueue.add() method for basic functionality.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
        testAddMultipleElements();
    }

    static void testAddWithNull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.add(null);
            // Expecting NullPointerException, as add should not accept nulls
            System.out.println("testAddWithNull: Failed - no exception thrown");
        } catch (NullPointerException expected) {
            System.out.println("testAddWithNull: Passed");
        } catch (Exception e) {
            System.out.println("testAddWithNull: Failed - unexpected exception: " + e);
        }
    }

    static void testAddWithValidElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean result = queue.add(10);
        if (result && queue.contains(10)) {
            System.out.println("testAddWithValidElement: Passed");
        } else {
            System.out.println("testAddWithValidElement: Failed");
        }
    }

    static void testAddMultipleElements() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        String[] elements = {"b", "a", "c"};
        boolean allAdded = true;
        for (String elem : elements) {
            boolean result = queue.add(elem);
            if (!result || !queue.contains(elem)) {
                allAdded = false;
                break;
            }
        }
        if (allAdded) {
            // Verify the order is as per natural ordering
            String first = queue.peek();
            if ("a".equals(first)) {
                System.out.println("testAddMultipleElements: Passed");
            } else {
                System.out.println("testAddMultipleElements: Failed - wrong head: " + first);
            }
        } else {
            System.out.println("testAddMultipleElements: Failed - not all elements added");
        }
    }
}
