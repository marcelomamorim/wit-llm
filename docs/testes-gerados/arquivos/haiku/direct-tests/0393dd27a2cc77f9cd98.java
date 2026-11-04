/*
 * @test
 * @summary Verifies LinkedBlockingDeque.drainTo(Collection) moves all current elements in FIFO order and leaves the deque empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        emptyDequeReturnsZero();
        drainsAllInFifoOrder();
        preservesExistingCollectionContent();
        boundedDequeIsDrainedAndReusable();
        collectionConstructorElementsAreDrained();
        wildcardSupertypeTarget();
    }

    static void emptyDequeReturnsZero() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        List<Integer> sink = new ArrayList<>();
        check(d.drainTo(sink) == 0, "empty deque must drain 0 elements");
        check(sink.isEmpty(), "sink must remain empty");
    }

    static void drainsAllInFifoOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 5; i++) {
            d.add(i);
        }
        List<Integer> sink = new ArrayList<>();
        check(d.drainTo(sink) == 5, "must return 5 drained elements");
        check(sink.equals(Arrays.asList(1, 2, 3, 4, 5)), "elements must be in FIFO order");
        check(d.isEmpty(), "deque must be empty after drain");
        check(d.size() == 0, "size must be 0 after drain");
        check(d.drainTo(sink) == 0, "second drain must return 0");
    }

    static void preservesExistingCollectionContent() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        List<Integer> sink = new ArrayList<>();
        sink.add(99);
        d.add(7);
        d.add(8);
        check(d.drainTo(sink) == 2, "must return 2 drained elements");
        check(sink.equals(Arrays.asList(99, 7, 8)), "elements must be appended after existing content");
    }

    static void boundedDequeIsDrainedAndReusable() {
        LinkedBlockingDeque<String> b = new LinkedBlockingDeque<>(2);
        b.add("a");
        b.add("b");
        List<String> sink = new ArrayList<>();
        check(b.drainTo(sink) == 2, "bounded deque must drain 2 elements");
        check(sink.equals(Arrays.asList("a", "b")), "bounded deque elements must be in order");
        check(b.isEmpty(), "bounded deque must be empty after drain");
        check(b.offer("c"), "capacity must be available again after drain");
        check(b.size() == 1, "size must be 1 after re-adding");
    }

    static void collectionConstructorElementsAreDrained() {
        LinkedBlockingDeque<Integer> f = new LinkedBlockingDeque<>(Arrays.asList(4, 5, 6));
        List<Integer> sink = new ArrayList<>();
        check(f.drainTo(sink) == 3, "must drain 3 elements built from collection");
        check(sink.equals(Arrays.asList(4, 5, 6)), "elements must keep collection order");
        check(f.isEmpty(), "deque must be empty after drain");
    }

    static void wildcardSupertypeTarget() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(10);
        d.add(20);
        Collection<Object> sink = new ArrayList<Object>();
        check(d.drainTo(sink) == 2, "must drain into Collection<Object>");
        check(sink.size() == 2 && sink.contains(10) && sink.contains(20), "sink must contain drained elements");
    }
}
