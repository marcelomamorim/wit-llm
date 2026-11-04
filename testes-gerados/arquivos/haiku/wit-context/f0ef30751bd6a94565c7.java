/*
 * @test
 * @summary Checks Collections.asLIFOQueue view forEach: null action throws NullPointerException and iteration follows LIFO order
 * @run main CollectionsWitupTest
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testLifoIterationOrder();
        testEmptyQueueForEachVisitsNothing();
    }

    static void testNullActionThrowsNPE() {
        Queue<String> q = Collections.asLIFOQueue(new ArrayDeque<String>());
        q.add("a");
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null action");
        }
    }

    static void testLifoIterationOrder() {
        Queue<String> q = Collections.asLIFOQueue(new ArrayDeque<String>());
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        List<String> expected = new ArrayList<>();
        expected.add("c");
        expected.add("b");
        expected.add("a");
        if (!expected.equals(seen)) {
            throw new AssertionError("unexpected order: " + seen);
        }
    }

    static void testEmptyQueueForEachVisitsNothing() {
        Queue<String> q = Collections.asLIFOQueue(new ArrayDeque<String>());
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        if (!seen.isEmpty()) {
            throw new AssertionError("expected no elements but got: " + seen);
        }
    }
}
