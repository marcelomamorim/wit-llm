/* @test    @summary Test for ArrayDeque.offer(E e) method to verify behavior with null input.    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithValidElement();
    }

    static void testOfferWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            boolean result = deque.offer(null);
            // Expecting a NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    static void testOfferWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offer("element");
        if (!result) {
            System.err.println("offer() returned false unexpectedly");
            System.exit(1);
        }
        if (deque.isEmpty()) {
            System.err.println("Deque should not be empty after offer");
            System.exit(1);
        }
        if (!deque.peekLast().equals("element")) {
            System.err.println("Element not added at the end of deque");
            System.exit(1);
        }
    }
}
