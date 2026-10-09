/* @test    @summary Test get(long, TimeUnit) method of CompletedFuture class    @run main CompletedFutureWitupTest_Pdd6a71402ab3 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletedFutureWitupTest_Pdd6a71402ab3 {

    public static void main(String[] args) {
        testGetWithResult();
        testGetWithFailure();
        testGetWithNullTimeUnit();
    }

    static void testGetWithResult() {
        String expectedResult = "test";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        try {
            String result = future.get(1, TimeUnit.SECONDS);
            if (!expectedResult.equals(result)) {
                throw new AssertionError("Expected: " + expectedResult + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetWithResult", e);
        }
    }

    static void testGetWithFailure() {
        IOException ioException = new IOException("fail");
        CompletedFuture<String> future = CompletedFuture.withFailure(ioException);
        try {
            future.get(1, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException was not thrown");
        } catch (ExecutionException e) {
            if (e.getCause() != ioException && !(e.getCause() instanceof IOException)) {
                throw new AssertionError("Unexpected cause in ExecutionException", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetWithFailure", e);
        }
    }

    static void testGetWithNullTimeUnit() {
        String expectedResult = "null";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        try {
            future.get(1, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetWithNullTimeUnit", e);
        }
    }
}
