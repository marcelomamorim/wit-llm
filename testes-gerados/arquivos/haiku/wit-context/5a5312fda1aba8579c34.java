/*
 * @test
 * @summary Observable behavior of LinkedBlockingDeque.toArray() on the current checkout
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyDeque();
        testOrderPreserved();
        testReturnedArrayIsCopy();
        testCapacityBounded();
        testAfterPoll();
        System.out.println("OK");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        Object[] a = d.toArray();
        check(a != null, "toArray must not return null");
        check(a.length == 0, "empty deque must yield empty array");
    }

    static void testOrderPreserved() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        d.addFirst("z");
        d.offerLast("b");
        Object[] a = d.toArray();
        check(Arrays.equals(a, new Object[]{"z", "a", "b"}),
              "toArray must preserve deque order from first to last");
    }

    static void testReturnedArrayIsCopy() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        Object[] a = d.toArray();
        check(a.length == 3, "length must equal deque size");
        a[0] = 99;
        check(d.peekFirst() == 1, "modifying returned array must not affect deque");
        check(d.size() == 3, "size must be unchanged after modifying returned array");
        Object[] b = d.toArray();
        check(Arrays.equals(b, new Object[]{1, 2, 3}),
              "subsequent toArray must reflect unmodified deque contents");
    }

    static void testCapacityBounded() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(2);
        check(d.offer("x"), "offer below capacity must succeed");
        check(d.offer("y"), "offer below capacity must succeed");
        check(!d.offer("w"), "offer at capacity must return false");
        Object[] a = d.toArray();
        check(Arrays.equals(a, new Object[]{"x", "y"}),
              "toArray must contain only accepted elements of bounded deque");
    }

    static void testAfterPoll() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(1);
        d.add(2);
        d.add(3);
        d.pollFirst();
        check(Arrays.equals(d.toArray(), new Object[]{2, 3}),
              "toArray must reflect removal from head");
        d.pollLast();
        check(Arrays.equals(d.toArray(), new Object[]{2}),
              "toArray must reflect removal from tail");
    }
}
