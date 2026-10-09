/*
 * @test
 * @summary Test sun.nio.ch.CompletedFuture.get(long, TimeUnit) behavior
 * @run main CompletedFutureWitupTest_P56996a2a282f
 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletedFutureWitupTest_P56996a2a282f {
    public static void main(String[] args) throws Exception {
        testGetWithNullUnitThrows();
        testGetWithResultReturnsResult();
        testGetWithFailureThrowsExecutionException();
    }

    private static void testGetWithNullUnitThrows() {
        CompletedFuture<String> cf = CompletedFuture.withResult("test");
        try {
            cf.get(10L, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }
    }

    private static void testGetWithResultReturnsResult() throws Exception {
        String expected = "hello";
        CompletedFuture<String> cf = CompletedFuture.withResult(expected);
        String actual = cf.get(100L, TimeUnit.MILLISECONDS);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected result '" + expected + "' but got '" + actual + "'");
        }
    }

    private static void testGetWithFailureThrowsExecutionException() {
        IOException ioex = new IOException("fail");
        CompletedFuture<String> cf = CompletedFuture.withFailure(ioex);
        try {
            cf.get(1L, TimeUnit.SECONDS);
            throw new RuntimeException("Expected ExecutionException not thrown");
        } catch (ExecutionException ee) {
            if (ee.getCause() != ioex) {
                throw new RuntimeException("ExecutionException cause mismatch", ee);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }
    }
}
