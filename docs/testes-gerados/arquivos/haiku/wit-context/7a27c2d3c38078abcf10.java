/*
 * @test
 * @summary Witup-derived tests for CompletableFuture.obtrudeException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testObtrudeOverridesCompletedValue();
        testObtrudeOnIncompleteFuture();
    }

    static void testNullThrowsNPE() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        if (cf.isDone()) {
            throw new AssertionError("future must remain incomplete after NPE");
        }
    }

    static void testObtrudeOverridesCompletedValue() throws InterruptedException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("value");
        IllegalStateException ex = new IllegalStateException("boom");
        cf.obtrudeException(ex);
        assertFailedWith(cf, ex);
    }

    static void testObtrudeOnIncompleteFuture() throws InterruptedException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        IllegalArgumentException ex = new IllegalArgumentException("bad");
        cf.obtrudeException(ex);
        assertFailedWith(cf, ex);
    }

    static void assertFailedWith(CompletableFuture<?> cf, Throwable ex) throws InterruptedException {
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("future should be completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("get() should have thrown ExecutionException");
        } catch (ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
        }
    }
}
