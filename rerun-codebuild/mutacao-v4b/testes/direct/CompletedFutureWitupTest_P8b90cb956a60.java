/* @test
   @summary Verifies the behavior of get(long, TimeUnit) method in CompletedFuture.
   @run main CompletedFutureWitupTest_P8b90cb956a60
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import sun.nio.ch.CompletedFuture;

public class CompletedFutureWitupTest_P8b90cb956a60 {
    public static void main(String[] args) throws Exception {
        testGetWithResult();
        testGetWithFailure();
        testGetWithNullTimeUnit();
    }

    private static void testGetWithResult() throws Exception {
        String expectedResult = "test";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        String result = future.get(1, TimeUnit.SECONDS);
        if (!expectedResult.equals(result)) {
            throw new AssertionError("Expected: " + expectedResult + ", but got: " + result);
        }
    }

    private static void testGetWithFailure() {
        IOException ioException = new IOException("fail");
        CompletedFuture<String> future = CompletedFuture.withFailure(ioException);
        try {
            future.get(1, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException");
        } catch (ExecutionException e) {
            if (e.getCause() != ioException && !(e.getCause() instanceof IOException)) {
                throw new AssertionError("Unexpected cause", e);
            }
        }
    }

    private static void testGetWithNullTimeUnit() {
        String expectedResult = "test";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        try {
            future.get(1, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
