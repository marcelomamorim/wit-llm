/*
 * @test
 * @summary Verifies that completeExceptionally on the minimal completion stage throws UnsupportedOperationException and leaves the stage and its source unchanged
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteExceptionallyThrowsOnMinimalStage();
        testCompleteExceptionallyWithNullThrowsOnMinimalStage();
    }

    static void testCompleteExceptionallyThrowsOnMinimalStage() {
        CompletableFuture<String> source = new CompletableFuture<>();
        CompletionStage<String> stage = source.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;

        boolean thrown = false;
        try {
            minimal.completeExceptionally(new RuntimeException("boom"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "completeExceptionally must throw UnsupportedOperationException");
        check(!minimal.isDone(), "minimal stage must not be completed after failed call");
        check(!source.isDone(), "source future must not be completed by minimal stage call");
    }

    static void testCompleteExceptionallyWithNullThrowsOnMinimalStage() {
        CompletableFuture<String> source = new CompletableFuture<>();
        CompletableFuture<String> minimal =
            (CompletableFuture<String>) source.minimalCompletionStage();

        boolean thrown = false;
        try {
            minimal.completeExceptionally(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "completeExceptionally(null) must throw UnsupportedOperationException");
        check(!minimal.isDone(), "minimal stage must remain incomplete");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
