/* @test
   @summary Test for CompletableFuture.failedFuture(Throwable ex) method.
   @run main CompletableFutureWitupTest_Pf11ad81c0c9a
 */
import java.util.concurrent.CompletableFuture;
public class CompletableFutureWitupTest_Pf11ad81c0c9a {
    public static void main(String[] args) {
        Throwable cause = new NullPointerException();
        CompletableFuture<?> future = CompletableFuture.failedFuture(cause);
        if (future == null) {
            throw new AssertionError("failedFuture returned null");
        }
        if (!future.isDone()) {
            throw new AssertionError("Future should be completed");
        }
        try {
            Object result = future.get();
            throw new AssertionError("Expected an exception, but got result: " + result);
        } catch (Exception e) {
            Throwable causeInException = e.getCause();
            if (causeInException == null) {
                throw new AssertionError("Exception has no cause", e);
            }
            if (!(causeInException instanceof NullPointerException)) {
                throw new AssertionError("Expected cause to be NullPointerException, but was: " + causeInException);
            }
        }
    }
}
