/*
 * @test
 * @summary Test CompletableFuture.getNumberOfDependents throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.getNumberOfDependents();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
        System.out.println("Test passed");
    }
}
