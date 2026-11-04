/*
 * @test
 * @summary Test java.util.ArrayDeque.offer(E) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();

        // Test offer with a normal element
        boolean offered = deque.offer("test");
        if (!offered) {
            throw new RuntimeException("offer returned false for a valid element");
        }

        // Test that the element is actually added at the end
        String polled = deque.pollLast();
        if (!"test".equals(polled)) {
            throw new RuntimeException("Element offered was not found at the end of the deque");
        }

        // Test offer with null element should throw NullPointerException
        boolean npeThrown = false;
        try {
            deque.offer(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("offer(null) did not throw NullPointerException");
        }

        // Test multiple offers
        deque.offer("one");
        deque.offer("two");
        deque.offer("three");

        // Check order by polling from front
        String first = deque.pollFirst();
        String second = deque.pollFirst();
        String third = deque.pollFirst();

        if (!"one".equals(first) || !"two".equals(second) || !"three".equals(third)) {
            throw new RuntimeException("Elements are not in expected order after multiple offers");
        }

        System.out.println("All tests passed.");
    }
}
