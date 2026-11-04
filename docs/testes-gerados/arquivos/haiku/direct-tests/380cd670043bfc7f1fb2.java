/*
 * @test
 * @summary Checks ArrayDeque.addLast: null rejection, FIFO order with peekLast, growth and wraparound
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullRejected();
        testSingleElementPeekLast();
        testGrowthPreservesOrder();
        testWraparoundPreservesOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        ArrayDeque<String> d = new ArrayDeque<>();
        boolean thrown = false;
        try {
            d.addLast(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "addLast(null) must throw NullPointerException");
        check(d.isEmpty(), "deque must remain empty after rejected null");
        check(d.size() == 0, "size must remain 0 after rejected null");
    }

    static void testSingleElementPeekLast() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.addLast("a");
        check(d.size() == 1, "size after one addLast must be 1");
        check("a".equals(d.peekLast()), "peekLast must return the added element");
        check("a".equals(d.peekFirst()), "peekFirst must return the only element");
    }

    static void testGrowthPreservesOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>(1);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            d.addLast(i);
            check(d.size() == i + 1, "size must grow with each addLast");
            check(d.peekLast() == i, "peekLast must return the latest element");
        }
        check(d.peekFirst() == 0, "peekFirst must be the first added element");
        for (int i = 0; i < n; i++) {
            Integer got = d.pollFirst();
            check(got != null && got == i, "FIFO order violated after growth");
        }
        check(d.isEmpty(), "deque must be empty after draining");
    }

    static void testWraparoundPreservesOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>(4);
        int total = 5000;
        int[] model = new int[total];
        int mh = 0;
        int mt = 0;
        for (int i = 0; i < total; i++) {
            d.addLast(i);
            model[mt++] = i;
            if (i % 3 == 0) {
                Integer got = d.pollFirst();
                check(got != null && got == model[mh++], "order violated during wraparound");
            }
            check(d.size() == mt - mh, "size mismatch during wraparound");
            check(d.peekLast() == i, "peekLast mismatch during wraparound");
        }
        while (!d.isEmpty()) {
            Integer got = d.pollFirst();
            check(got != null && got == model[mh++], "order violated while draining");
        }
        check(mh == mt, "all modeled elements must be drained");
    }
}
