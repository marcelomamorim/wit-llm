/* @test    @summary Test for CompletableFuture.complete method to verify behavior and exception handling    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteReturnsTrueOnSuccess();
        testCompleteThrowsUnsupportedOperationException();
    }

    private static void testCompleteReturnsTrueOnSuccess() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        boolean result = cf.complete("test");
        if (!result) {
            throw new AssertionError("Expected complete to return true");
        }
        if (!"test".equals(cf.getNow(null))) {
            throw new AssertionError("Expected value to be 'test'");
        }
    }

    private static void testCompleteThrowsUnsupportedOperationException() {
        CompletableFuture<String> cf = new CompletableFuture<String>() {
            @Override
            public boolean complete(String value) {
                throw new UnsupportedOperationException();
            }
        };
        try {
            cf.complete("fail");
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
