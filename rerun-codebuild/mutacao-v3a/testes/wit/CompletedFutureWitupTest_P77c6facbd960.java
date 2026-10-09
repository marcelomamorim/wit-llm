/*
 * @test
 * @summary Tests for sun.nio.ch.CompletedFuture.get(long, TimeUnit)
 * @run main CompletedFutureWitupTest_P77c6facbd960
 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletedFutureWitupTest_P77c6facbd960 {

    public static void main(String[] args) {
        testGetWithNullTimeUnit();
        testGetWithException();
        testGetSuccessful();
    }

    private static void testGetWithNullTimeUnit() {
        try {
            CompletedFuture<String> cf = CompletedFuture.withResult("ok");
            cf.get(1000L, null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testGetWithException() {
        try {
            Throwable exc = new IOException("error");
            CompletedFuture<String> cf = CompletedFuture.withFailure(exc);
            cf.get(1000L, TimeUnit.MILLISECONDS);
            System.err.println("Failed: Expected ExecutionException not thrown");
        } catch (ExecutionException e) {
            if (!(e.getCause() instanceof IOException)) {
                System.err.println("Failed: Unexpected cause in ExecutionException: " + e.getCause());
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testGetSuccessful() {
        try {
            String result = "success";
            CompletedFuture<String> cf = CompletedFuture.withResult(result);
            String obtained = cf.get(1000L, TimeUnit.MILLISECONDS);
            if (!"success".equals(obtained)) {
                System.err.println("Failed: Unexpected result: " + obtained);
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
