/* @test    @summary Test getNumberOfDependents method of CompletableFuture    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetNumberOfDependents();
    }

    public static void testGetNumberOfDependents() {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        int result = future.getNumberOfDependents();
        if (result != 0) {
            throw new AssertionError("Expected 0, but got " + result);
        }
    }
}
