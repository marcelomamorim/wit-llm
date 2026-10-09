/*
 * @test
 * @summary Test sun.nio.ch.CompletedFuture.get(long, TimeUnit) method behavior
 * @run main CompletedFutureWitupTest_P56996a2a282f
 */

import sun.nio.ch.CompletedFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class CompletedFutureWitupTest_P56996a2a282f {
    public static void main(String[] args) throws Exception {
        testGetWithNullUnitThrowsNPE();
        testGetWithFailureThrowsExecutionException();
        testGetWithResultReturnsValue();
    }

    private static void testGetWithNullUnitThrowsNPE() {
        CompletedFuture<String> cf = CompletedFuture.withResult("test");
        try {
            cf.get(10L, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    private static void testGetWithFailureThrowsExecutionException() {
        IOException ioex = new IOException("fail");
        CompletedFuture<String> cf = CompletedFuture.withFailure(ioex);
        try {
            cf.get(10L, TimeUnit.MILLISECONDS);
            throw new AssertionError("Expected ExecutionException not thrown");
        } catch (ExecutionException ee) {
            if (ee.getCause() != ioex) {
                throw new AssertionError("ExecutionException cause mismatch", ee);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    private static void testGetWithResultReturnsValue() throws Exception {
        String expected = "success";
        CompletedFuture<String> cf = CompletedFuture.withResult(expected);
        String actual = cf.get(10L, TimeUnit.SECONDS);
        if (!expected.equals(actual)) {
            throw new AssertionError("Returned result mismatch: expected=" + expected + ", actual=" + actual);
        }
    }
}
