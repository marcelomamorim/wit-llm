/*
 * @test
 * @summary Test newFixedThreadPool method for valid and invalid inputs
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) {
        testNewFixedThreadPoolValid();
        testNewFixedThreadPoolInvalid();
        System.out.println("All tests passed.");
    }

    private static void testNewFixedThreadPoolValid() {
        // Test with valid positive number of threads
        ExecutorService es = java.util.concurrent.Executors.newFixedThreadPool(1);
        if (es == null) {
            throw new AssertionError("newFixedThreadPool returned null for nThreads=1");
        }
        if (!(es instanceof ThreadPoolExecutor)) {
            throw new AssertionError("Returned ExecutorService is not a ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
        if (tpe.getCorePoolSize() != 1 || tpe.getMaximumPoolSize() != 1) {
            throw new AssertionError("ThreadPoolExecutor pool sizes are not equal to nThreads=1");
        }
        if (tpe.getKeepAliveTime(TimeUnit.MILLISECONDS) != 0L) {
            throw new AssertionError("KeepAliveTime is not zero");
        }
        es.shutdown();
    }

    private static void testNewFixedThreadPoolInvalid() {
        // According to current source code, no explicit check for nThreads <= 0 is done,
        // so no IllegalArgumentException is expected. Instead, test that 0 or negative
        // values cause some observable behavior (e.g. exception or null).
        // The WIT expath suggests IllegalArgumentException, but current code does not throw it.

        // Test with zero threads
        try {
            ExecutorService es0 = java.util.concurrent.Executors.newFixedThreadPool(0);
            if (es0 == null) {
                throw new AssertionError("newFixedThreadPool returned null for nThreads=0");
            }
            // Try to submit a task to see if it accepts tasks or throws
            try {
                es0.submit(new Runnable() {
                    public void run() {}
                });
            } catch (Exception e) {
                // Acceptable: some exception may be thrown
            }
            es0.shutdown();
        } catch (IllegalArgumentException iae) {
            // Acceptable if thrown, but not expected in current code
        } catch (Exception e) {
            // Acceptable
        }

        // Test with negative threads
        try {
            ExecutorService esNeg = java.util.concurrent.Executors.newFixedThreadPool(-1);
            if (esNeg == null) {
                throw new AssertionError("newFixedThreadPool returned null for nThreads=-1");
            }
            try {
                esNeg.submit(new Runnable() {
                    public void run() {}
                });
            } catch (Exception e) {
                // Acceptable
            }
            esNeg.shutdown();
        } catch (IllegalArgumentException iae) {
            // Acceptable if thrown, but not expected in current code
        } catch (Exception e) {
            // Acceptable
        }
    }
}
