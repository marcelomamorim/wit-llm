/*
 * @test
 * @summary Witup-generated behavioral tests for ArrayBlockingQueue iterator forEachRemaining
 * @run main ArrayBlockingQueueWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueVisitsNothing();
        testVisitsAllElementsInOrder();
        testPartialConsumptionVisitsRest();
        testWrappedArrayOrder();
        testIteratorExhaustedAfterCall();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> collect(Iterator<Integer> it) {
        final List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(x -> seen.add(x));
        return seen;
    }

    static void testNullActionThrowsNpe() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(1);
        Iterator<Integer> it = q.iterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyQueueVisitsNothing() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        List<Integer> seen = collect(q.iterator());
        check(seen.isEmpty(), "empty queue must not invoke action: " + seen);
    }

    static void testVisitsAllElementsInOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> seen = collect(q.iterator());
        check(seen.equals(Arrays.asList(1, 2, 3)), "unexpected order: " + seen);
    }

    static void testPartialConsumptionVisitsRest() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        Iterator<Integer> it = q.iterator();
        int first = it.next();
        check(first == 1, "first element expected 1 but was " + first);
        List<Integer> rest = collect(it);
        check(rest.equals(Arrays.asList(2, 3, 4)), "unexpected remaining elements: " + rest);
    }

    static void testWrappedArrayOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        Integer removed = q.poll();
        check(removed != null && removed == 1, "poll should return 1");
        q.add(4);
        List<Integer> seen = collect(q.iterator());
        check(seen.equals(Arrays.asList(2, 3, 4)), "wrapped order wrong: " + seen);
    }

    static void testIteratorExhaustedAfterCall() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(7);
        q.add(8);
        Iterator<Integer> it = q.iterator();
        collect(it);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }
}
