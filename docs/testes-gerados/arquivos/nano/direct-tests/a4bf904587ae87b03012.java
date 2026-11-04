/* @test    @summary Test CompletableFuture.runAsync with a simple Runnable    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testRunAsyncCompletes();
        testRunAsyncWithException();
    }

    static void testRunAsyncCompletes() {
        final boolean[] executed = {false};
        Runnable r = () -> {
            executed[0] = true;
        };
        CompletableFuture<Void> future = CompletableFuture.runAsync(r);
        future.join();
        assert executed[0] : "Runnable was not executed";
    }

    static void testRunAsyncWithException() {
        Runnable r = () -> {
            throw new RuntimeException("Test exception");
        };
        CompletableFuture<Void> future = CompletableFuture.runAsync(r);
        try {
            future.join();
            assert false : "Expected exception was not thrown";
        } catch (Throwable t) {
            Throwable cause = t.getCause();
            assert cause instanceof RuntimeException : "Cause is not RuntimeException";
            assert "Test exception".equals(cause.getMessage()) : "Unexpected exception message";
        }
    }
}
