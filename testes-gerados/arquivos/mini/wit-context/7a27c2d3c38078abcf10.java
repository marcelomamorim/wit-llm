/*
 * @test
 * @summary Test CompletableFuture.obtrudeException(Throwable) for null argument and normal usage
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testObtrudeExceptionWithNull();
        testObtrudeExceptionWithValidException();
    }

    private static void testObtrudeExceptionWithNull() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    private static void testObtrudeExceptionWithValidException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        RuntimeException rex = new RuntimeException("test");
        cf.obtrudeException(rex);
        // After obtrudeException, cf should be completed exceptionally with rex
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture not done after obtrudeException");
        }
        try {
            cf.join();
            throw new AssertionError("Expected RuntimeException not thrown on join");
        } catch (RuntimeException e) {
            if (e != rex && e.getCause() != rex) {
                throw new AssertionError("Exception thrown by join is not the one obtruded", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type on join", t);
        }
    }
}
