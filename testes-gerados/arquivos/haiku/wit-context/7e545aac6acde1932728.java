/*
 * @test
 * @summary Verifies that put(null) on the queue returned by ScheduledThreadPoolExecutor.getQueue() throws NullPointerException and leaves the queue unchanged
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> queue = stpe.getQueue();
            int before = queue.size();
            boolean thrown = false;
            try {
                queue.put(null);
            } catch (NullPointerException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("put(null) should throw NullPointerException");
            }
            if (queue.size() != before) {
                throw new AssertionError("queue size changed after rejected put(null)");
            }
        } finally {
            stpe.shutdownNow();
        }
    }
}
