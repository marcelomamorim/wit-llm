/*
 * @test
 * @summary Verifies observable behavior of CompletableFuture.obtrudeException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testObtrudeOnNewFutureCompletesExceptionally();
        testObtrudeReplacesNormalValue();
        testObtrudeReplacesPreviousException();
    }

    static void testNullThrowsNullPointerException() {
        CompletableFuture<String> f = new CompletableFuture<>();
        try {
            f.obtrudeException(null);
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        assertFalse(f.isDone(), "future must remain incomplete after NPE");
    }

    static void testObtrudeOnNewFutureCompletesExceptionally() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        RuntimeException ex = new IllegalStateException("boom");
        f.obtrudeException(ex);
        assertTrue(f.isDone(), "future should be done");
        assertTrue(f.isCompletedExceptionally(), "future should be exceptional");
        try {
            f.get();
            fail("expected ExecutionException");
        } catch (ExecutionException e) {
            assertSame(ex, e.getCause(), "get() cause");
        }
        try {
            f.join();
            fail("expected CompletionException");
        } catch (CompletionException e) {
            assertSame(ex, e.getCause(), "join() cause");
        }
    }

    static void testObtrudeReplacesNormalValue() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        f.complete("value");
        RuntimeException ex = new IllegalArgumentException("obtruded");
        f.obtrudeException(ex);
        assertTrue(f.isCompletedExceptionally(), "future should now be exceptional");
        try {
            f.get();
            fail("expected ExecutionException");
        } catch (ExecutionException e) {
            assertSame(ex, e.getCause(), "get() cause after obtrude");
        }
    }

    static void testObtrudeReplacesPreviousException() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        f.completeExceptionally(new RuntimeException("first"));
        RuntimeException second = new RuntimeException("second");
        f.obtrudeException(second);
        try {
            f.join();
            fail("expected CompletionException");
        } catch (CompletionException e) {
            assertSame(second, e.getCause(), "join() cause after second obtrude");
        }
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new RuntimeException("assertTrue failed: " + msg);
    }

    static void assertFalse(boolean cond, String msg) {
        if (cond) throw new RuntimeException("assertFalse failed: " + msg);
    }

    static void assertSame(Object expected, Object actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException("assertSame failed: " + msg);
        }
    }

    static void fail(String msg) {
        throw new RuntimeException("fail: " + msg);
    }
}
