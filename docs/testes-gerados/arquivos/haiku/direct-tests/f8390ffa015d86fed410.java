/*
 * @test
 * @summary Verifies that complete(T) on the minimal CompletionStage view throws UnsupportedOperationException and leaves the source future unchanged
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testPlainCompleteStillWorks();
        testMinimalStageCompleteThrows();
        testMinimalStageDoesNotCompleteSource();
    }

    static void testPlainCompleteStillWorks() {
        CompletableFuture<String> f = new CompletableFuture<>();
        check(f.complete("value"), "complete on plain future should return true");
        check("value".equals(f.join()), "plain future should hold completed value");
    }

    static void testMinimalStageCompleteThrows() {
        CompletableFuture<String> source = new CompletableFuture<>();
        CompletionStage<String> stage = source.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        boolean thrown = false;
        try {
            minimal.complete("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "complete(T) on minimal stage should throw UnsupportedOperationException");
    }

    static void testMinimalStageDoesNotCompleteSource() {
        CompletableFuture<String> source = new CompletableFuture<>();
        CompletableFuture<String> minimal =
            (CompletableFuture<String>) source.minimalCompletionStage();
        try {
            minimal.complete("x");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        check(!source.isDone(), "source future must remain incomplete after failed complete");
        check(!minimal.isDone(), "minimal stage must remain incomplete after failed complete");
    }
}
