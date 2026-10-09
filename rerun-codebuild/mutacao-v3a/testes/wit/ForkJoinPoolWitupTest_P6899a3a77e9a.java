/* @test    @summary Test ForkJoinPool.execute with null Runnable to confirm it throws NullPointerException    @run main ForkJoinPoolWitupTest_P6899a3a77e9a */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest_P6899a3a77e9a {

    public static void main(String[] args) {
        testExecuteWithNullRunnable();
    }

    public static void testExecuteWithNullRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        boolean exceptionThrown = false;
        try {
            pool.execute(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when executing null Runnable");
        }
    }
}
