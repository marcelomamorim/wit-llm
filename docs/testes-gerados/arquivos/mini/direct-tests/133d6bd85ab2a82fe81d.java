/*
 * @test
 * @summary Basic tests for Executors.newFixedThreadPool(int) method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewFixedThreadPoolPositive();
        testNewFixedThreadPoolZero();
        testNewFixedThreadPoolNegative();
    }

    static void testNewFixedThreadPoolPositive() throws Exception {
        int nThreads = 2;
        ExecutorService es = java.util.concurrent.Executors.newFixedThreadPool(nThreads);
        if (!(es instanceof ThreadPoolExecutor)) {
            throw new RuntimeException("Returned ExecutorService is not a ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
        if (tpe.getCorePoolSize() != nThreads) {
            throw new RuntimeException("Core pool size mismatch: expected " + nThreads + " but got " + tpe.getCorePoolSize());
        }
        if (tpe.getMaximumPoolSize() != nThreads) {
            throw new RuntimeException("Maximum pool size mismatch: expected " + nThreads + " but got " + tpe.getMaximumPoolSize());
        }
        if (tpe.getKeepAliveTime(TimeUnit.MILLISECONDS) != 0L) {
            throw new RuntimeException("Keep alive time mismatch: expected 0 but got " + tpe.getKeepAliveTime(TimeUnit.MILLISECONDS));
        }
        if (!(tpe.getQueue() instanceof LinkedBlockingQueue)) {
            throw new RuntimeException("Queue is not a LinkedBlockingQueue");
        }
        es.shutdown();
        if (!es.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("ExecutorService did not terminate in time");
        }
    }

    static void testNewFixedThreadPoolZero() {
        try {
            java.util.concurrent.Executors.newFixedThreadPool(0);
            throw new RuntimeException("Expected IllegalArgumentException for nThreads=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNewFixedThreadPoolNegative() {
        try {
            java.util.concurrent.Executors.newFixedThreadPool(-1);
            throw new RuntimeException("Expected IllegalArgumentException for nThreads=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
