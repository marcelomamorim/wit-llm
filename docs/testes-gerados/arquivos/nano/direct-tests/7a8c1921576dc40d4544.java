/* @test    @summary Test join() method of CompletableFuture for UnsupportedOperationException    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testJoinThrowsUnsupported();
    }

    public static void testJoinThrowsUnsupported() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.join();
            // If no exception, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
