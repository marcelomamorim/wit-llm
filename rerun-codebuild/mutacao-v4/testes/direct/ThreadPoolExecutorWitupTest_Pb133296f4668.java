/* @test
   @summary Verifies the behavior of allowCoreThreadTimeOut method in ThreadPoolExecutor.
   @run main ThreadPoolExecutorWitupTest_Pb133296f4668
 */
import java.util.concurrent.ThreadPoolExecutor;
public class ThreadPoolExecutorWitupTest_Pb133296f4668 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Test setting allowCoreThreadTimeOut to true with valid keepAliveTime
        executor.allowCoreThreadTimeOut(true);
        // Verify that the method executes without exception and changes the internal state
        // Since internal state is not directly accessible, we rely on no exception being thrown
        // and the method completing normally
        // Test setting allowCoreThreadTimeOut to false
        executor.allowCoreThreadTimeOut(false);
        // Again, verify no exception occurs
        // Test invalid case: keepAliveTime <= 0 when setting allowCoreThreadTimeOut to true
        // For this, we need to simulate the condition; but since keepAliveTime is private,
        // we assume the initial setup is valid and focus on the exception case
        try {
            ThreadPoolExecutor invalidExecutor = new ThreadPoolExecutor(1, 2, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
            invalidExecutor.allowCoreThreadTimeOut(true); // should throw IllegalArgumentException
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
