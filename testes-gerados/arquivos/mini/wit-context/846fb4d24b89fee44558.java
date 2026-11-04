/*
 * @test
 * @summary Test ForkJoinWorkerThread.setContextClassLoader throws SecurityException
 * @run main ForkJoinWorkerThreadWitupTest
 */

public class ForkJoinWorkerThreadWitupTest {
    public static void main(String[] args) {
        final java.util.concurrent.ForkJoinPool pool = new java.util.concurrent.ForkJoinPool();
        final java.util.concurrent.ForkJoinWorkerThread worker = new java.util.concurrent.ForkJoinWorkerThread(pool) {};
        try {
            worker.setContextClassLoader(null);
            throw new Error("Expected SecurityException not thrown");
        } catch (SecurityException se) {
            // Expected exception
        }
        pool.shutdown();
    }
}
