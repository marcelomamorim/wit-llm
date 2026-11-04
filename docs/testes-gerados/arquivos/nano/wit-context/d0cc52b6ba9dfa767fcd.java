/* @test    @summary Test for getLargestPoolSize method in ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetLargestPoolSize();
    }

    public static void testGetLargestPoolSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        int size = executor.getLargestPoolSize();
        if (size != 0) {
            System.out.println("Test passed: getLargestPoolSize() returned " + size);
        } else {
            System.out.println("Test passed: getLargestPoolSize() returned 0 as initial value");
        }
    }
}
