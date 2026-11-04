/*
 * @test
 * @summary Test for removeAll(Collection<?>) method in ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeAll(null);
            System.out.println("FAIL: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        boolean result = deque.removeAll(Arrays.asList());
        if (result && deque.size() == 3) {
            System.out.println("PASS: removeAll with empty collection did not remove elements");
        } else {
            System.out.println("FAIL: removeAll with empty collection altered deque or returned false");
        }
    }

    static void testRemoveAllWithNonEmptyCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3, 4, 5));
        boolean result = deque.removeAll(Arrays.asList(2, 4));
        if (result && deque.size() == 3 && deque.containsAll(Arrays.asList(1, 3, 5))) {
            System.out.println("PASS: removeAll removed specified elements");
        } else {
            System.out.println("FAIL: removeAll did not behave as expected");
        }
    }
}
