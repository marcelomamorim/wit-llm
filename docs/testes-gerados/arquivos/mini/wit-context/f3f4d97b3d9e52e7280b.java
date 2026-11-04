/*
 * @test
 * @summary Test CompletableFuture.get(long, TimeUnit) throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.get(1L, TimeUnit.SECONDS);
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new Error("Unexpected exception type: " + t);
        }
    }
}
