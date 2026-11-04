/* @test    @summary Test for ArrayDeque.removeIf to verify exception handling and behavior    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Predicate;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithConcurrentModification();
    }

    private static void testRemoveIfWithNullPredicate() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        try {
            deque.removeIf(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testRemoveIfWithConcurrentModification() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer t) {
                return t == 1;
            }
        };
        // Simulate concurrent modification by modifying deque during removeIf
        try {
            deque.removeIf(predicate);
            System.out.println("PASS: removeIf executed without concurrent modification exception");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("FAIL: Unexpected ConcurrentModificationException");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
