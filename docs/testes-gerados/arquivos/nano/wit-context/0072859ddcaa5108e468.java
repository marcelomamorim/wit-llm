/*
 * @test
 * @summary Test for ArrayBlockingQueue.removeIf method behavior and exception paths
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfValidPredicate();
    }

    private static void testRemoveIfNullPredicate() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        // Add some elements
        queue.add(1);
        queue.add(2);
        queue.add(3);
        try {
            queue.removeIf(null);
            System.out.println("FAIL: Expected NullPointerException for null predicate");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null predicate");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveIfValidPredicate() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean result = false;
        try {
            result = queue.removeIf(i -> i % 2 == 1); // Remove odd numbers
            if (result) {
                System.out.println("PASS: removeIf with predicate returned true");
            } else {
                System.out.println("FAIL: removeIf with predicate returned false");
            }
            // Verify remaining elements
            boolean contains2 = queue.contains(2);
            boolean contains1 = queue.contains(1);
            boolean contains3 = queue.contains(3);
            if (contains2 && !contains1 && !contains3) {
                System.out.println("PASS: Queue correctly removed odd elements");
            } else {
                System.out.println("FAIL: Queue contents incorrect after removeIf");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("FAIL: Unexpected UnsupportedOperationException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
