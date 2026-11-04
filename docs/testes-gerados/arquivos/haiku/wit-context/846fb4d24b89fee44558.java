/*
 * @test
 * @summary Verifies setContextClassLoader and getContextClassLoader on a ForkJoinWorkerThread subclass built with the protected constructor
 * @run main ForkJoinWorkerThreadWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class ForkJoinWorkerThreadWitupTest {

    static class TestWorkerThread extends ForkJoinWorkerThread {
        TestWorkerThread(ForkJoinPool pool) {
            super(pool);
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            TestWorkerThread t = new TestWorkerThread(pool);

            ClassLoader cl = ForkJoinWorkerThreadWitupTest.class.getClassLoader();
            t.setContextClassLoader(cl);
            if (t.getContextClassLoader() != cl) {
                throw new RuntimeException("context class loader not set");
            }

            t.setContextClassLoader(null);
            if (t.getContextClassLoader() != null) {
                throw new RuntimeException("context class loader not cleared to null");
            }
        } finally {
            pool.shutdown();
        }
    }
}
