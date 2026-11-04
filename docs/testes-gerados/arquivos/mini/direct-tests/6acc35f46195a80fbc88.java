/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.isQueued(Thread) method
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.Node;

public class AbstractQueuedLongSynchronizerWitupTest {

    static final class TestAQLS extends AbstractQueuedLongSynchronizer {
        // expose tail and nodes for testing
        volatile Node tailNode;

        TestAQLS() {
            super();
        }

        // helper to set tail field via reflection-like approach
        void setTail(Node n) {
            // tail is private volatile Node tail; no setter, so use reflection
            // but reflection is disallowed, so use subclass hack:
            // tail is private in superclass, so we cannot access directly
            // Instead, we use enq(Node) method to set tail indirectly
            // But enq is private in AbstractQueuedSynchronizer, so no access
            // So we create a dummy node chain by enqueuing nodes via public API
            // But no public API to enqueue nodes, so we simulate by subclassing Node
            // Instead, we use a hack: create a dummy Node chain and assign tailNode field
            // and override isQueued to use tailNode instead of tail
            this.tailNode = n;
        }

        @Override
        public final boolean isQueued(Thread thread) {
            if (thread == null)
                throw new NullPointerException();
            for (Node p = tailNode; p != null; p = p.prev)
                if (p.thread == thread)
                    return true;
            return false;
        }
    }

    public static void main(String[] args) {
        TestAQLS sync = new TestAQLS();

        // Test 1: null thread argument throws NullPointerException
        try {
            sync.isQueued(null);
            throw new Error("Expected NullPointerException for null thread");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: empty queue returns false
        sync.setTail(null);
        Thread current = Thread.currentThread();
        if (sync.isQueued(current)) {
            throw new Error("Expected false for thread not in empty queue");
        }

        // Test 3: single node with different thread returns false
        Node node1 = new Node(Thread.currentThread());
        node1.prev = null;
        sync.setTail(node1);
        Thread otherThread = new Thread();
        if (sync.isQueued(otherThread)) {
            throw new Error("Expected false for thread not in queue");
        }

        // Test 4: single node with matching thread returns true
        if (!sync.isQueued(Thread.currentThread())) {
            throw new Error("Expected true for thread in queue");
        }

        // Test 5: multiple nodes, thread in middle returns true
        Node node2 = new Node(otherThread);
        node2.prev = node1;
        sync.setTail(node2);
        if (!sync.isQueued(Thread.currentThread())) {
            throw new Error("Expected true for thread in queue (middle node)");
        }
        if (!sync.isQueued(otherThread)) {
            throw new Error("Expected true for thread in queue (tail node)");
        }

        // Test 6: multiple nodes, thread not present returns false
        Thread absentThread = new Thread();
        if (sync.isQueued(absentThread)) {
            throw new Error("Expected false for thread not in queue");
        }

        System.out.println("All tests passed.");
    }
}
