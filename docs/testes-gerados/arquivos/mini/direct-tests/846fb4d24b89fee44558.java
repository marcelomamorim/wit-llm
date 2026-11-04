/*
 * @test
 * @summary Test that ForkJoinWorkerThread.setContextClassLoader always throws SecurityException
 * @run main ForkJoinWorkerThreadWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class ForkJoinWorkerThreadWitupTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(1);
        ForkJoinWorkerThread worker = new ForkJoinWorkerThread(pool) {};
        try {
            worker.setContextClassLoader(null);
            throw new RuntimeException("Expected SecurityException not thrown");
        } catch (SecurityException se) {
            // Expected exception
        } finally {
            pool.shutdown();
        }
    }
}
