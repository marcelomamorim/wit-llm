/*
 * @test
 * @summary Tests for LinkedBlockingDeque.addLast method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddLastWithNull();
        testAddLastWithObject();
    }

    private static void testAddLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.addLast(null);
            // Expecting NullPointerException as addLast calls offerLast which throws if null
            System.err.println("Failed: NullPointerException not thrown for null element");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null element: " + e);
        }
    }

    private static void testAddLastWithObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        try {
            deque.addLast("element");
            if (deque.isEmpty()) {
                System.err.println("Failed: Element not added");
            } else {
                System.out.println("Passed: Element added successfully");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid element: " + e);
        }
    }
}
