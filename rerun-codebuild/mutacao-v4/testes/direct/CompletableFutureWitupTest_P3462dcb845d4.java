/* @test
   @summary Verifies the behavior of orTimeout method in CompletableFuture.
   @run main CompletableFutureWitupTest_P3462dcb845d4
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
public class CompletableFutureWitupTest_P3462dcb845d4 {
    public static void main(String[] args) {
        testOrTimeoutWithNullUnit();
        testOrTimeoutReturnsSameInstance();
    }
    private static void testOrTimeoutWithNullUnit() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
    private static void testOrTimeoutReturnsSameInstance() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        CompletableFuture<Void> result = cf.orTimeout(1, TimeUnit.SECONDS);
        if (result != cf) {
            throw new AssertionError("orTimeout should return the same instance") ;
        }
    }
}
