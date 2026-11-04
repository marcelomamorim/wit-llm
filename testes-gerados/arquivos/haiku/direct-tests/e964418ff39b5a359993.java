/*
 * @test
 * @summary Behavioral tests for LinkedBlockingQueue.toArray(T[] a)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testLargeArrayIsReusedAndTerminatedWithNull();
        testSmallArrayIsReplacedByNewArray();
        testEmptyQueueWithEmptyArray();
        testEmptyQueueClearsFirstSlot();
        testArrayStoreExceptionKeepsQueueUsable();
        testNullArrayThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testLargeArrayIsReusedAndTerminatedWithNull() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        String[] arr = {"x", "y", "z", "w", "v"};
        String[] r = q.toArray(arr);
        check(r == arr, "expected same array instance");
        check("a".equals(r[0]), "r[0] should be a");
        check("b".equals(r[1]), "r[1] should be b");
        check("c".equals(r[2]), "r[2] should be c");
        check(r[3] == null, "slot after last element should be null");
        check("v".equals(r[4]), "slots beyond k+1 should be untouched");
    }

    static void testSmallArrayIsReplacedByNewArray() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        String[] arr = new String[1];
        String[] r = q.toArray(arr);
        check(r != arr, "expected a new array instance");
        check(r.length == 2, "expected length equal to queue size");
        check("a".equals(r[0]), "r[0] should be a");
        check("b".equals(r[1]), "r[1] should be b");
        check(arr[0] == null, "input array should not be modified");
        check(q.size() == 2, "queue size must be unchanged");
    }

    static void testEmptyQueueWithEmptyArray() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        String[] arr = new String[0];
        String[] r = q.toArray(arr);
        check(r == arr, "expected same empty array instance");
        check(r.length == 0, "expected length 0");
    }

    static void testEmptyQueueClearsFirstSlot() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        String[] arr = {"x", "y"};
        String[] r = q.toArray(arr);
        check(r == arr, "expected same array instance");
        check(r[0] == null, "first slot should be null for empty queue");
        check("y".equals(r[1]), "slot beyond first should be untouched");
    }

    static void testArrayStoreExceptionKeepsQueueUsable() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.toArray(new String[1]);
        } catch (ArrayStoreException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayStoreException for incompatible array type");
        check(q.offer(2), "offer should succeed after exception (lock released)");
        check(q.size() == 2, "queue should contain both elements");
    }

    static void testNullArrayThrowsNullPointerException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        String[] nullArr = null;
        boolean thrown = false;
        try {
            q.toArray(nullArr);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
        check(q.offer("b"), "offer should succeed after exception (lock released)");
        check(q.size() == 2, "queue should contain two elements");
    }
}
