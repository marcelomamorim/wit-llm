/* @test    @summary Tests setMaximumPoolSize method of ThreadPoolExecutor for valid and invalid inputs    @run main ThreadPoolExecutorWitupTest_P52dbe128b819 */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P52dbe128b819 {

    public static void main(String[] args) {
        testSetMaximumPoolSizeValid();
        testSetMaximumPoolSizeInvalid();
    }

    public static void testSetMaximumPoolSizeValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        int initialMaxSize = executor.getMaximumPoolSize();
        executor.setMaximumPoolSize(initialMaxSize + 1);
        assert executor.getMaximumPoolSize() == initialMaxSize + 1 : "Maximum pool size should be increased by 1";
        System.out.println("testSetMaximumPoolSizeValid passed");
    }

    public static void testSetMaximumPoolSizeInvalid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 5, 500L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(0);
            assert false : "Expected IllegalArgumentException for zero maximumPoolSize";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            executor.setMaximumPoolSize(-1);
            assert false : "Expected IllegalArgumentException for negative maximumPoolSize";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            executor.setMaximumPoolSize(executor.getCorePoolSize() - 1);
            assert false : "Expected IllegalArgumentException for maximumPoolSize less than corePoolSize";
        } catch (IllegalArgumentException e) {
            // expected
        }
        System.out.println("testSetMaximumPoolSizeInvalid passed");
    }
}
