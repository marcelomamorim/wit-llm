/*
 * @test
 * @summary Witup-derived tests for PriorityBlockingQueue.drainTo(Collection)
 * @run main PriorityBlockingQueueWitupTest
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testEmptyQueueReturnsZero();
        testDrainsAllInPriorityOrder();
        testAppendsToExistingTarget();
        testComparatorDrivesOrder();
    }

    static void check(boolean ok, String msg) {
        if (!ok) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullCollectionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.drainTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null collection");
        check(q.size() == 1, "queue must be unchanged after NPE");
    }

    static void testEmptyQueueReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        List<Integer> target = new ArrayList<>();
        int n = q.drainTo(target);
        check(n == 0, "expected 0 drained from empty queue, got " + n);
        check(target.isEmpty(), "target must remain empty");
    }

    static void testDrainsAllInPriorityOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(4);
        q.add(2);
        q.add(3);
        List<Integer> target = new ArrayList<>();
        int n = q.drainTo(target);
        check(n == 5, "expected 5 drained, got " + n);
        check(target.toString().equals("[1, 2, 3, 4, 5]"), "unexpected order: " + target);
        check(q.isEmpty(), "queue must be empty after drainTo");
    }

    static void testAppendsToExistingTarget() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(2);
        q.add(1);
        List<Integer> target = new ArrayList<>();
        target.add(0);
        int n = q.drainTo(target);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.toString().equals("[0, 1, 2]"), "unexpected contents: " + target);
        check(q.isEmpty(), "queue must be empty after drainTo");
    }

    static void testComparatorDrivesOrder() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.add(1);
        q.add(3);
        q.add(2);
        List<Integer> target = new ArrayList<>();
        int n = q.drainTo(target);
        check(n == 3, "expected 3 drained, got " + n);
        check(target.toString().equals("[3, 2, 1]"), "unexpected comparator order: " + target);
    }
}
