/*
 * @test
 * @summary Test CompletableFuture.obtrudeException behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testObtrudeExceptionNull();
        testObtrudeExceptionSetsException();
    }

    static void testObtrudeExceptionNull() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testObtrudeExceptionSetsException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("test");
        cf.obtrudeException(ex);
        try {
            cf.join();
            throw new Error("Expected RuntimeException");
        } catch (RuntimeException e) {
            if (e != ex && e.getCause() != ex) {
                throw new Error("Exception thrown by join() is not the obtruded exception");
            }
        }
    }
}
