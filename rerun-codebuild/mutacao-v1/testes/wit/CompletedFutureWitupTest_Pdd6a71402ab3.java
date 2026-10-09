/* @test    @summary Test for sun.nio.ch.CompletedFuture.get(long, TimeUnit) method    @run main CompletedFutureWitupTest_Pdd6a71402ab3 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletedFutureWitupTest_Pdd6a71402ab3 {

    public static void main(String[] args) throws Exception {
        testGetWithNullTimeUnit();
        testGetWithException();
    }

    private static void testGetWithNullTimeUnit() {
        sun.nio.ch.CompletedFuture<String> future = sun.nio.ch.CompletedFuture.withResult("result");
        try {
            future.get(1000L, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetWithException() {
        RuntimeException ex = new RuntimeException("test exception");
        sun.nio.ch.CompletedFuture<String> future = sun.nio.ch.CompletedFuture.withFailure(ex);
        try {
            future.get(1000L, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException not thrown");
        } catch (ExecutionException e) {
            if (e.getCause() != ex && !(e.getCause() instanceof RuntimeException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
