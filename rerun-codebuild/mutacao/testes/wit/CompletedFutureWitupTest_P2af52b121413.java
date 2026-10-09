/* @test    @summary Test for sun.nio.ch.CompletedFuture.get(long, TimeUnit) method    @run main CompletedFutureWitupTest_P2af52b121413 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletedFutureWitupTest_P2af52b121413 {

    public static void main(String[] args) {
        testGetWithNullTimeUnit();
        testGetWithExceptionAndValidTimeout();
    }

    private static void testGetWithNullTimeUnit() {
        try {
            CompletedFuture<Object> future = CompletedFuture.withResult("test");
            future.get(1000L, null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null TimeUnit");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testGetWithExceptionAndValidTimeout() {
        try {
            Throwable exc = new IOException("test exception");
            CompletedFuture<Object> future = CompletedFuture.withFailure(exc);
            future.get(1000L, TimeUnit.SECONDS);
            System.err.println("Failed: Expected ExecutionException not thrown");
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IOException) {
                System.out.println("Passed: ExecutionException with IOException cause as expected");
            } else {
                System.err.println("Failed: Cause is not IOException: " + e.getCause());
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
