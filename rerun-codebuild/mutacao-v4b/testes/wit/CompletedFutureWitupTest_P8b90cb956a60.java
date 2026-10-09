/* @test
   @summary Testa o método get(long, TimeUnit) de sun.nio.ch.CompletedFuture.
   @run main CompletedFutureWitupTest_P8b90cb956a60
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import sun.nio.ch.CompletedFuture;

public class CompletedFutureWitupTest_P8b90cb956a60 {
    public static void main(String[] args) {
        testGetWithNullTimeUnit();
        testGetWithException();
        testGetNormalResult();
    }

    private static void testGetWithNullTimeUnit() {
        CompletedFuture<Object> future = CompletedFuture.withResult(new Object());
        try {
            future.get(10L, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetWithException() {
        Throwable exc = new IOException("test exception");
        CompletedFuture<Object> future = CompletedFuture.withFailure(exc);
        try {
            future.get(10L, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException");
        } catch (ExecutionException e) {
            if (e.getCause() != exc && !(e.getCause() instanceof IOException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetNormalResult() {
        String result = "success";
        CompletedFuture<String> future = CompletedFuture.withResult(result);
        try {
            String value = future.get(10L, TimeUnit.SECONDS);
            if (!"success".equals(value)) {
                throw new AssertionError("Unexpected result: " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
