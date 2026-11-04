/*
 * @test
 * @summary Verify getNumberOfDependents on minimal completion stage throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        CompletableFuture<String> base = new CompletableFuture<>();
        check(base.getNumberOfDependents() == 0, "fresh future should have 0 dependents");

        base.thenRun(() -> { });
        check(base.getNumberOfDependents() == 1, "one dependent expected after thenRun");

        CompletionStage<String> minimal = base.minimalCompletionStage();
        CompletableFuture<String> minimalCf = minimal.toCompletableFuture();
        boolean thrown = false;
        try {
            minimalCf.getNumberOfDependents();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "getNumberOfDependents on minimal stage must throw UnsupportedOperationException");
    }
}
