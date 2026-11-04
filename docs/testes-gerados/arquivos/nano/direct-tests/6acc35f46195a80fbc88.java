/* @test    @summary Test isQueued method of AbstractQueuedLongSynchronizer    @run main AbstractQueuedLongSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testIsQueuedWithCurrentThread();
        testIsQueuedWithDifferentThread();
        testIsQueuedWithNull();
    }

    private static void testIsQueuedWithCurrentThread() throws InterruptedException {
        final DummySynchronizer sync = new DummySynchronizer();
        Thread t = new Thread(() -> {
            sync.acquire();
        });
        t.start();
        Thread.sleep(50); // Ensure thread is queued
        boolean result = sync.isQueued(t);
        System.out.println("Test isQueued with current thread: " + result);
        assert result : "Expected thread to be queued";
        t.join();
    }

    private static void testIsQueuedWithDifferentThread() throws InterruptedException {
        final DummySynchronizer sync = new DummySynchronizer();
        Thread t1 = new Thread(() -> {
            sync.acquire();
        });
        Thread t2 = new Thread(() -> {
            // do nothing
        });
        t1.start();
        t2.start();
        Thread.sleep(50); // Ensure t1 is queued
        boolean result = sync.isQueued(t2);
        System.out.println("Test isQueued with different thread: " + result);
        assert !result : "Expected thread not to be queued";
        t1.join();
        t2.join();
    }

    private static void testIsQueuedWithNull() {
        final DummySynchronizer sync = new DummySynchronizer();
        boolean exceptionThrown = false;
        try {
            sync.isQueued(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
            System.out.println("Test isQueued with null thread: NullPointerException thrown as expected.");
        }
        assert exceptionThrown : "Expected NullPointerException when thread is null";
    }

    static class DummySynchronizer extends AbstractQueuedLongSynchronizer {
        public void acquire() {
            acquire(1);
        }
        @Override
        public boolean tryAcquire(long arg) {
            Thread current = Thread.currentThread();
            Node node = new Node(current);
            enq(node);
            return true;
        }
        private void enq(Node node) {
            for (;;) {
                Node t = tail;
                if (t == null) {
                    if (compareAndSetHead(new Node())) {
                        tail = head;
                    }
                } else {
                    node.prev = t;
                    if (compareAndSetTail(t, node)) {
                        t.next = node;
                        break;
                    }
                }
            }
        }
        private boolean compareAndSetHead(Node update) {
            // Simplified CAS for test purposes
            head = update;
            return true;
        }
        private boolean compareAndSetTail(Node expect, Node update) {
            // Simplified CAS for test purposes
            tail = update;
            return true;
        }
    }

    static class Node {
        final Thread thread;
        Node prev;
        Node next;
        Node() {
            this.thread = null;
        }
        Node(Thread thread) {
            this.thread = thread;
        }
    }
}
