/*
 * @test
 * @summary Verify ConcurrentLinkedQueue.forEach rejects null action and traverses elements in FIFO order
 * @run main ConcurrentLinkedQueueWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTraversalOrder();
        testEmptyQueueNoActionCalls();
        testQueueUnchangedAfterForEach();
        System.out.println("PASSED");
    }

    static void testNullActionThrowsNPE() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null action");
        }
    }

    static void testTraversalOrder() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        if (seen.size() != 3
                || !"a".equals(seen.get(0))
                || !"b".equals(seen.get(1))
                || !"c".equals(seen.get(2))) {
            throw new AssertionError("unexpected traversal order: " + seen);
        }
    }

    static void testEmptyQueueNoActionCalls() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        int[] calls = new int[1];
        q.forEach(s -> calls[0]++);
        if (calls[0] != 0) {
            throw new AssertionError("action invoked " + calls[0] + " times on empty queue");
        }
    }

    static void testQueueUnchangedAfterForEach() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("x");
        q.add("y");
        q.forEach(s -> { });
        if (q.size() != 2 || !"x".equals(q.peek())) {
            throw new AssertionError("queue modified by forEach, size=" + q.size());
        }
    }
}
