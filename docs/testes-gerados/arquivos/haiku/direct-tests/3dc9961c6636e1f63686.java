/*
 * @test
 * @summary Tests CompletableFuture.orTimeout null-unit check, return value, timeout completion and no-op on completed futures
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullUnitOnIncompleteFuture();
        testNullUnitOnCompletedFuture();
        testReturnsSameInstance();
        testTimeoutCompletesExceptionally();
        testCompletedFutureUnaffected();
    }

    static void testNullUnitOnIncompleteFuture() {
        CompletableFuture<String> f = new CompletableFuture<>();
        try {
            f.orTimeout(1, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null unit");
    }

    static void testNullUnitOnCompletedFuture() {
        CompletableFuture<String> f = CompletableFuture.completedFuture("x");
        try {
            f.orTimeout(1, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null unit on completed future");
    }

    static void testReturnsSameInstance() {
        CompletableFuture<String> f = new CompletableFuture<>();
        CompletableFuture<String> r = f.orTimeout(10, TimeUnit.MILLISECONDS);
        if (r != f) {
            throw new RuntimeException("orTimeout must return this");
        }
        f.complete("done");
    }

    static void testTimeoutCompletesExceptionally() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        CompletableFuture<String> r = f.orTimeout(10, TimeUnit.MILLISECONDS);
        if (r != f) {
            throw new RuntimeException("orTimeout must return this");
        }
        try {
            f.get();
            throw new RuntimeException("Expected ExecutionException");
        } catch (ExecutionException e) {
            if (!(e.getCause() instanceof TimeoutException)) {
                throw new RuntimeException("Expected TimeoutException cause but got " + e.getCause());
            }
        }
        if (!f.isCompletedExceptionally()) {
            throw new RuntimeException("Future should be completed exceptionally after timeout");
        }
    }

    static void testCompletedFutureUnaffected() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        f.complete("v");
        CompletableFuture<String> r = f.orTimeout(1, TimeUnit.MILLISECONDS);
        if (r != f) {
            throw new RuntimeException("orTimeout must return this");
        }
        Thread.sleep(50);
        if (f.isCompletedExceptionally()) {
            throw new RuntimeException("Completed future must not be completed exceptionally");
        }
        String value = f.get();
        if (!"v".equals(value)) {
            throw new RuntimeException("Expected value v but got " + value);
        }
    }
}
