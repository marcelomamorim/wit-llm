/*
 * @test
 * @summary Verifies getNumberOfDependents on CompletableFuture and the UnsupportedOperationException on the minimal stage view
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        if (cf.getNumberOfDependents() != 0) {
            throw new RuntimeException("expected 0 dependents on a fresh future");
        }

        cf.thenRun(() -> { });
        if (cf.getNumberOfDependents() != 1) {
            throw new RuntimeException("expected 1 dependent after thenRun");
        }

        CompletionStage<String> minimal = cf.minimalCompletionStage();
        CompletableFuture<String> minimalFuture = (CompletableFuture<String>) minimal;
        boolean thrown = false;
        try {
            minimalFuture.getNumberOfDependents();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from minimal stage");
        }
    }
}
