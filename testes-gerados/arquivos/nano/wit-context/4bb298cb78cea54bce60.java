/*
 * @test
 * @summary Test for LinkedBlockingDeque.remove() method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveEmptyDeque();
        testRemoveSingleElement();
        testRemoveMultipleElements();
    }

    static void testRemoveEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.remove();
            System.out.println("FAIL: Expected NoSuchElementException for empty deque");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Caught expected NoSuchElementException for empty deque");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    static void testRemoveSingleElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        String removed = deque.remove();
        if ("element".equals(removed) && deque.isEmpty()) {
            System.out.println("PASS: Successfully removed single element");
        } else {
            System.out.println("FAIL: Unexpected behavior removing single element");
        }
    }

    static void testRemoveMultipleElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Integer first = deque.remove();
        if (first == 1 && deque.size() == 2) {
            System.out.println("PASS: Removed first element correctly");
        } else {
            System.out.println("FAIL: Unexpected behavior removing multiple elements");
        }
        // Remove remaining elements
        deque.remove();
        deque.remove();
        if (deque.isEmpty()) {
            System.out.println("PASS: All elements removed, deque is empty");
        } else {
            System.out.println("FAIL: Deque not empty after removing all elements");
        }
    }
}
