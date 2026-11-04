/*
 * @test
 * @summary Behavioural tests for LinkedBlockingDeque.toArray()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        Object[] a = d.toArray();
        check(a != null, "toArray returned null");
        check(a.length == 0, "empty deque should yield empty array");
        check(a.getClass() == Object[].class, "result must be Object[]");
    }

    static void testOrderPreserved() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.offerLast(1);
        d.offerLast(2);
        d.offerLast(3);
        Object[] a = d.toArray();
        check(a.length == 3, "length mismatch");
        check(a[0].equals(1) && a[1].equals(2) && a[2].equals(3), "order not head-to-tail");
    }

    static void testOfferFirstOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.offerLast(1);
        d.offerLast(2);
        d.offerFirst(0);
        Object[] a = d.toArray();
        check(a.length == 3, "length mismatch after offerFirst");
        check(a[0].equals(0) && a[1].equals(1) && a[2].equals(2), "offerFirst element not at head");
    }

    static void testReturnedArrayIsCopy() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.offerLast(10);
        d.offerLast(20);
        Object[] a = d.toArray();
        a[0] = Integer.valueOf(99);
        Object[] b = d.toArray();
        check(b[0].equals(10), "modifying returned array must not affect deque");
        check(d.size() == 2, "deque size changed unexpectedly");
    }

    static void testReflectsRemoval() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.offerLast(1);
        d.offerLast(2);
        d.offerLast(3);
        d.poll();
        Object[] a = d.toArray();
        check(a.length == 2, "array should reflect poll");
        check(a[0].equals(2) && a[1].equals(3), "wrong contents after poll");
    }

    static void testBoundedDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(5);
        d.offerLast("a");
        d.offerLast("b");
        Object[] a = d.toArray();
        check(a.length == 2, "bounded deque length mismatch");
        check(a[0].equals("a") && a[1].equals("b"), "bounded deque contents mismatch");
    }

    public static void main(String[] args) {
        testEmptyDeque();
        testOrderPreserved();
        testOfferFirstOrder();
        testReturnedArrayIsCopy();
        testReflectsRemoval();
        testBoundedDeque();
    }
}
