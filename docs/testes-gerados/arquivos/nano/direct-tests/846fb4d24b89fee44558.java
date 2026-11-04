/*
 * @test    @summary Test setContextClassLoader method of ForkJoinWorkerThread
 * @run main ForkJoinWorkerThreadWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class ForkJoinWorkerThreadWitupTest {

    public static void main(String[] args) {
        testSetContextClassLoaderThrows();
    }

    public static void testSetContextClassLoaderThrows() {
        ForkJoinPool pool = new ForkJoinPool(1);
        ForkJoinWorkerThread worker = null;
        try {
            worker = (ForkJoinWorkerThread) pool.getWorkerThreadFactory().newThread(null);
        } catch (Exception e) {
            // Fallback: create directly if factory is not accessible
            worker = new ForkJoinWorkerThread(pool) {};
        }
        ClassLoader cl = null;
        boolean exceptionThrown = false;
        try {
            worker.setContextClassLoader(cl);
        } catch (SecurityException se) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected SecurityException was not thrown.");
        }
        pool.shutdown();
    }
}
