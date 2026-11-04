/*
 * @test
 * @summary Checks the observable format and state reporting of ThreadPoolExecutor.toString()
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testNewExecutorReportsRunning();
        testShutdownEmptyExecutorReportsTerminated();
        testCompletedTaskIsCounted();
    }

    private static void testNewExecutorReportsRunning() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            String s = e.toString();
            check(s.startsWith("java.util.concurrent.ThreadPoolExecutor@"),
                    "unexpected prefix: " + s);
            check(s.endsWith("[Running, pool size = 0, active threads = 0, "
                    + "queued tasks = 0, completed tasks = 0]"),
                    "unexpected state: " + s);
        } finally {
            e.shutdownNow();
        }
    }

    private static void testShutdownEmptyExecutorReportsTerminated() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        String s = e.toString();
        check(s.endsWith("[Terminated, pool size = 0, active threads = 0, "
                + "queued tasks = 0, completed tasks = 0]"),
                "unexpected state after shutdown: " + s);
    }

    private static void testCompletedTaskIsCounted() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        e.execute(() -> { });
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        String s = e.toString();
        check(s.endsWith("[Terminated, pool size = 0, active threads = 0, "
                + "queued tasks = 0, completed tasks = 1]"),
                "completed task not reflected: " + s);
    }
}
