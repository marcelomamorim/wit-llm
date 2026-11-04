/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingQueue.toArray(T[])
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueLargerArrayGetsNullTerminator();
        testLargerArrayKeepsElementsAndTail();
        testSmallerArrayAllocatesNewArray();
        testExactSizeArrayReturnedAsIs();
        testNullArrayThrowsNullPointerException();
        testArrayStoreExceptionReleasesLock();
        testReturnedArrayComponentType();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueLargerArrayGetsNullTerminator() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        String[] a = {"x", "x", "x"};
        String[] r = q.toArray(a);
        check(r == a, "expected same array instance");
        check(a[0] == null, "expected a[0] to be null for empty queue");
        check("x".equals(a[1]) && "x".equals(a[2]), "tail elements beyond terminator must be untouched");
    }

    static void testLargerArrayKeepsElementsAndTail() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        String[] a = {"x", "x", "x", "x"};
        String[] r = q.toArray(a);
        check(r == a, "expected same array instance");
        check("a".equals(a[0]), "a[0] should be a");
        check("b".equals(a[1]), "a[1] should be b");
        check(a[2] == null, "a[2] should be null terminator");
        check("x".equals(a[3]), "a[3] should be untouched");
    }

    static void testSmallerArrayAllocatesNewArray() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("p");
        q.add("q");
        q.add("r");
        String[] a = new String[1];
        String[] r = q.toArray(a);
        check(r != a, "expected a new array when argument is too small");
        check(r.length == 3, "expected length 3, got " + r.length);
        check("p".equals(r[0]) && "q".equals(r[1]) && "r".equals(r[2]), "wrong contents");
        check(a[0] == null, "original small array must not be filled");
    }

    static void testExactSizeArrayReturnedAsIs() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("m");
        q.add("n");
        String[] a = new String[2];
        String[] r = q.toArray(a);
        check(r == a, "expected same array when length equals size");
        check("m".equals(a[0]) && "n".equals(a[1]), "wrong contents");
    }

    static void testNullArrayThrowsNullPointerException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        try {
            q.toArray((String[]) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }

    static void testArrayStoreExceptionReleasesLock() {
        LinkedBlockingQueue<Object> q = new LinkedBlockingQueue<>();
        q.add(Integer.valueOf(1));
        try {
            q.toArray(new String[1]);
            throw new RuntimeException("expected ArrayStoreException");
        } catch (ArrayStoreException expected) {
            // expected
        }
        check(q.size() == 1, "queue must remain usable after exception");
        check(q.offer(Integer.valueOf(2)), "offer must succeed after exception");
        check(q.size() == 2, "size should be 2");
    }

    static void testReturnedArrayComponentType() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("s");
        String[] r = q.toArray(new String[0]);
        check(r.getClass() == String[].class, "expected String[] return type");
        check(r.length == 1 && "s".equals(r[0]), "wrong contents");
    }
}
