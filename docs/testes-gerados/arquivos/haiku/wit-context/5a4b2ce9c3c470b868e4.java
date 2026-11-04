/*
 * @test
 * @summary Witup-generated check that the queue iterator of ScheduledThreadPoolExecutor reflects a snapshot of the scheduled tasks and never throws for a valid queue
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ses = new ScheduledThreadPoolExecutor(1);
        try {
            Iterator<Runnable> empty = ses.getQueue().iterator();
            check(!empty.hasNext(), "iterator of empty queue must have no elements");

            Runnable r1 = () -> { };
            ScheduledFuture<?> f1 = ses.schedule(r1, 1, TimeUnit.HOURS);

            Iterator<Runnable> it = ses.getQueue().iterator();
            check(it.hasNext(), "iterator must expose the scheduled task");
            Object first = it.next();
            check(first == f1, "iterator element must be the scheduled future");
            check(!it.hasNext(), "iterator must contain exactly one element");

            Iterator<Runnable> snapshot = ses.getQueue().iterator();
            ses.schedule(() -> { }, 2, TimeUnit.HOURS);

            int snapshotCount = countElements(snapshot);
            check(snapshotCount == 1, "earlier iterator must remain a snapshot, count=" + snapshotCount);

            int freshCount = countElements(ses.getQueue().iterator());
            check(freshCount == 2, "new iterator must reflect both tasks, count=" + freshCount);
        } finally {
            ses.shutdownNow();
        }
    }

    private static int countElements(Iterator<Runnable> it) {
        int n = 0;
        while (it.hasNext()) {
            it.next();
            n++;
        }
        return n;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
