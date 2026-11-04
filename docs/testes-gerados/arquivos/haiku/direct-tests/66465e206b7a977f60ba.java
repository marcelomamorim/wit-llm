/*
 * @test
 * @summary Verifies LinkedBlockingDeque.drainTo transfers elements in order and handles edge cases
 * @run main LinkedBlockingDequeWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static class FailingList extends ArrayList<Integer> {
        @Override
        public boolean add(Integer e) {
            if (size() == 1) {
                throw new IllegalStateException("boom");
            }
            return super.add(e);
        }
    }

    static void basicDrainInOrder() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> out = new ArrayList<>();
        int n = d.drainTo(out, 3);
        check(n == 3, "expected 3 but was " + n);
        check(out.equals(Arrays.asList(1, 2, 3)), "wrong order: " + out);
        check(d.size() == 2, "expected size 2");
        check(d.peekFirst() == 4, "expected head 4");
    }

    static void maxGreaterThanCount() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(7, 8));
        List<Integer> out = new ArrayList<>();
        int n = d.drainTo(out, Integer.MAX_VALUE);
        check(n == 2, "expected 2 but was " + n);
        check(out.equals(Arrays.asList(7, 8)), "wrong contents: " + out);
        check(d.isEmpty(), "deque should be empty");
    }

    static void nonPositiveMaxReturnsZero() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1, 2));
        List<Integer> out = new ArrayList<>();
        check(d.drainTo(out, 0) == 0, "max 0 should return 0");
        check(d.drainTo(out, -1) == 0, "negative max should return 0");
        check(out.isEmpty(), "collection should be untouched");
        check(d.size() == 2, "deque should be untouched");
    }

    static void emptyDequeReturnsZero() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        List<Integer> out = new ArrayList<>();
        check(d.drainTo(out, 5) == 0, "empty deque should return 0");
        check(out.isEmpty(), "collection should remain empty");
    }

    static void nullCollectionThrowsNpe() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1));
        try {
            d.drainTo(null, 1);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.size() == 1, "deque should be untouched");
    }

    static void selfDrainThrowsIae() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1, 2));
        try {
            d.drainTo(d, 1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(d.size() == 2, "deque should be untouched");
        check(d.peekFirst() == 1, "head should remain 1");
    }

    static void failingAddKeepsElementInDeque() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(10, 20, 30));
        FailingList out = new FailingList();
        try {
            d.drainTo(out, 3);
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(out.size() == 1, "collection should hold one element");
        check(out.get(0) == 10, "first transferred element should be 10");
        check(d.size() == 2, "failed element must remain in deque");
        check(d.peekFirst() == 20, "head should be 20");
    }

    public static void main(String[] args) {
        basicDrainInOrder();
        maxGreaterThanCount();
        nonPositiveMaxReturnsZero();
        emptyDequeReturnsZero();
        nullCollectionThrowsNpe();
        selfDrainThrowsIae();
        failingAddKeepsElementInDeque();
    }
}
