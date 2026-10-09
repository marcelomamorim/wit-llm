/* @test    @summary Test get(long, TimeUnit) method of CompletedFuture for normal and exceptional completion    @run main CompletedFutureWitupTest_P77c6facbd960 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import sun.nio.ch.CompletedFuture;

public class CompletedFutureWitupTest_P77c6facbd960 {

    public static void main(String[] args) {
        testGetWithResult();
        testGetWithFailure();
        testGetWithNullTimeUnit();
    }

    static void testGetWithResult() {
        String result = "testResult";
        CompletedFuture<String> future = CompletedFuture.withResult(result);
        try {
            String value = future.get(1, TimeUnit.SECONDS);
            if (!result.equals(value)) {
                throw new AssertionError("Expected: " + result + ", but got: " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    static void testGetWithFailure() {
        IOException ex = new IOException("test exception");
        CompletedFuture<Object> future = CompletedFuture.withFailure(ex);
        try {
            future.get(1, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException was not thrown");
        } catch (ExecutionException ee) {
            Throwable cause = ee.getCause();
            if (cause != ex && !cause.getMessage().equals("test exception")) {
                throw new AssertionError("Cause mismatch, expected: " + ex + ", but got: " + cause, cause);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    static void testGetWithNullTimeUnit() {
        String result = "nullTest";
        CompletedFuture<String> future = CompletedFuture.withResult(result);
        try {
            future.get(1, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
