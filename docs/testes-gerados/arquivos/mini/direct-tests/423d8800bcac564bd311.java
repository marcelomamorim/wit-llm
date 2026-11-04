/*
 * @test
 * @summary Test CompletableFuture.getNow throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        String defaultValue = "default";
        try {
            cf.getNow(defaultValue);
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected exception
        }
    }
}
