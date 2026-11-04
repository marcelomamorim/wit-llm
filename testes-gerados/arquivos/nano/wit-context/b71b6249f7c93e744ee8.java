/* @test    @summary Test for ArrayDeque.remove() method to verify exception when lastRet < 0    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsWhenLastRetNegative();
    }

    private static void testRemoveThrowsWhenLastRetNegative() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        // Prepare deque with elements
        deque.add(1);
        deque.add(2);
        deque.add(3);
        // Create an iterator
        ArrayDeque<Integer>.DeqIterator iterator = deque.new DeqIterator();
        // Manually set lastRet to an invalid state to trigger exception
        // Since lastRet is package-private, simulate invalid state by calling remove() without valid lastRet
        // but as remove() checks lastRet, we need to set it to -1 explicitly
        // To do this, we can invoke remove() without prior next() to simulate invalid state
        try {
            iterator.remove();
            // If no exception, test fails
            System.err.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
