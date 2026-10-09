/* @test    @summary Test for CompletableFuture.failedStage(Throwable ex) with null input    @run main CompletableFutureWitupTest_P6c1fc1bacf2a */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest_P6c1fc1bacf2a {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            // Should throw NullPointerException
            System.err.println("Failed: expected NullPointerException for null input.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: unexpected exception: " + t);
        }
    }

    static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            System.err.println("Failed: returned stage is null.");
            return;
        }
        if (stage.toString().contains("CompletableFuture")) {
            System.out.println("Passed: returned stage is a CompletableFuture.");
        } else {
            System.err.println("Failed: returned stage is not a CompletableFuture.");
        }
        // Additional check: the stage should complete exceptionally with the same exception
        try {
            stage.toCompletableFuture().get();
            System.err.println("Failed: expected exception on get(), but got none.");
        } catch (Throwable t) {
            if (t.getCause() == ex || t.getCause().equals(ex)) {
                System.out.println("Passed: stage completes exceptionally with the provided exception.");
            } else {
                System.err.println("Failed: exception cause does not match.");
            }
        }
    }
}
