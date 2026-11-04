/*
 * @test
 * @summary Test for ReferenceQueue.remove() method behavior
 * @run main ReferenceQueueWitupTest
 */

import java.lang.ref.ReferenceQueue;

public class ReferenceQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testRemoveReturnsNullWhenEmpty();
        testRemoveBlocksUntilElementEnqueued();
    }

    private static void testRemoveReturnsNullWhenEmpty() throws InterruptedException {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object result = queue.remove();
        assert result == null : "Expected null when queue is empty";
    }

    private static void testRemoveBlocksUntilElementEnqueued() throws InterruptedException {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Thread remover = new Thread(() -> {
            try {
                Object ref = queue.remove();
                assert ref != null : "Expected non-null reference after enqueue";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        remover.start();
        Thread.sleep(100); // Ensure the thread is blocking on remove()
        // Enqueue a dummy reference to unblock remove()
        // Since Reference is abstract, use a dummy subclass
        class DummyRef extends java.lang.ref.Reference<Object> {
            protected DummyRef() { super(null); }
            @Override public Object get() { return null; }
            @Override public void clear() { }
            @Override public boolean isEnqueued() { return false; }
        }
        DummyRef dummyRef = new DummyRef();
        queue.enqueue(dummyRef);
        remover.join(1000);
        assert !remover.isAlive() : "Remover thread should have finished";
    }
}
