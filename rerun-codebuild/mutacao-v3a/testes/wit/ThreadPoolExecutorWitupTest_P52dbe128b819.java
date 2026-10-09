/*
 * @test
 * @summary Tests behavior of ThreadPoolExecutor.setMaximumPoolSize(int)
 * @run main ThreadPoolExecutorWitupTest_P52dbe128b819
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest_P52dbe128b819 {

    public static void main(String[] args) {
        testSetMaximumPoolSizeThrowsOnInvalid();
        testSetMaximumPoolSizeNormal();
    }

    private static void testSetMaximumPoolSizeThrowsOnInvalid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS, null
        );
        try {
            // Expect IllegalArgumentException when maximumPoolSize <= 0
            executor.setMaximumPoolSize(0);
            System.out.println("FAIL: Expected IllegalArgumentException for 0");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for 0");
        }
        try {
            // Expect IllegalArgumentException when corePoolSize > maximumPoolSize
            executor.setCorePoolSize(2);
            executor.setMaximumPoolSize(1);
            System.out.println("FAIL: Expected IllegalArgumentException for corePoolSize > maximumPoolSize");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for corePoolSize > maximumPoolSize");
        }
    }

    private static void testSetMaximumPoolSizeNormal() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2, 4, 0L, TimeUnit.MILLISECONDS, null
        );
        try {
            executor.setMaximumPoolSize(3);
            if (executor.getMaximumPoolSize() == 3) {
                System.out.println("PASS: setMaximumPoolSize(3) succeeded");
            } else {
                System.out.println("FAIL: setMaximumPoolSize(3) did not set correctly");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
