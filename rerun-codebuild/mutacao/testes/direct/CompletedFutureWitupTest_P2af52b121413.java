/* @test    @summary Test get method with timeout and TimeUnit in CompletedFuture class    @run main CompletedFutureWitupTest_P2af52b121413 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletedFutureWitupTest_P2af52b121413 {

    public static void main(String[] args) {
        testGetWithTimeoutAndTimeUnitSuccess();
        testGetWithTimeoutAndTimeUnitNullUnit();
        testGetWithTimeoutAndTimeUnitWithException();
    }

    static void testGetWithTimeoutAndTimeUnitSuccess() {
        String expectedResult = "result";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        try {
            String result = future.get(1, TimeUnit.SECONDS);
            if (!expectedResult.equals(result)) {
                throw new AssertionError("Expected: " + expectedResult + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testGetWithTimeoutAndTimeUnitNullUnit() {
        String expectedResult = "result";
        CompletedFuture<String> future = CompletedFuture.withResult(expectedResult);
        try {
            future.get(1, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testGetWithTimeoutAndTimeUnitWithException() {
        IOException ioException = new IOException("IO error");
        CompletedFuture<String> future = CompletedFuture.withFailure(ioException);
        try {
            future.get(1, TimeUnit.SECONDS);
            throw new AssertionError("Expected ExecutionException was not thrown");
        } catch (ExecutionException e) {
            if (e.getCause() != ioException) {
                throw new AssertionError("Expected cause: " + ioException + ", but got: " + e.getCause());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
