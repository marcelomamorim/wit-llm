/*
 * @test
 * @summary Test for AbstractQueuedLongSynchronizer.isQueued(Thread)
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testIsQueuedWithNullThread();
        testIsQueuedWithNonQueuedThread();
        testIsQueuedWithQueuedThread();
    }

    private static void testIsQueuedWithNullThread() {
        AbstractQueuedLongSynchronizer synchronizer = new DummyAQLS();
        try {
            synchronizer.isQueued(null);
            System.out.println("FAIL: Expected NullPointerException for null thread");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testIsQueuedWithNonQueuedThread() {
        AbstractQueuedLongSynchronizer synchronizer = new DummyAQLS();
        Thread thread = new Thread();
        boolean result = synchronizer.isQueued(thread);
        if (result == false) {
            System.out.println("PASS: Thread not queued as expected");
        } else {
            System.out.println("FAIL: Thread should not be queued");
        }
    }

    private static void testIsQueuedWithQueuedThread() {
        DummyAQLS synchronizer = new DummyAQLS();
        Thread thread = new Thread();
        synchronizer.enqueueThread(thread);
        boolean result = synchronizer.isQueued(thread);
        if (result == true) {
            System.out.println("PASS: Queued thread detected as expected");
        } else {
            System.out.println("FAIL: Thread should be detected as queued");
        }
    }

    private static class DummyAQLS extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;
        private Node tailNode;

        public void enqueueThread(Thread thread) {
            Node node = new Node(thread);
            enqueue(node);
        }

        private void enqueue(Node node) {
            for (;;) {
                Node t = tail;
                if (compareAndSetTail(t, node)) {
                    if (t != null) {
                        t.next = node;
                        node.prev = t;
                    } else {
                        setHead(node);
                    }
                    break;
                }
            }
        }

        private boolean compareAndSetTail(Node expect, Node update) {
            synchronized (this) {
                if (tail == expect) {
                    tail = update;
                    return true;
                }
                return false;
            }
        }

        private void setHead(Node node) {
            // Not used in this test, but required for enqueue
        }
    }

    private static class Node {
        final Thread thread;
        Node prev;
        Node next;
        Node(Thread thread) {
            this.thread = thread;
        }
    }
}
