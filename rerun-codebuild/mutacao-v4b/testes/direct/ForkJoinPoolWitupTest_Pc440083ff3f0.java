/* @test
   @summary Tests ForkJoinPool.submit(Runnable) for null and normal Runnable.
   @run main ForkJoinPoolWitupTest_Pc440083ff3f0
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
public class ForkJoinPoolWitupTest_Pc440083ff3f0 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        // Test that passing null throws NullPointerException
        try {
            pool.submit((Runnable) null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        // Test that submitting a simple Runnable returns a ForkJoinTask
        Runnable task = () -> {};
        ForkJoinTask<?> fjTask = pool.submit(task);
        if (fjTask == null) {
            throw new AssertionError("submit() returned null");
        }
        // Optionally, check that the task is of expected type
        if (!(fjTask instanceof ForkJoinTask)) {
            throw new AssertionError("Returned task is not a ForkJoinTask");
        }
    }
}
