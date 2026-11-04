/* @test    @summary Test for CompletableFuture.failedStage(Throwable ex) method behavior.    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    private static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            System.err.println("Failed: expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null argument");
        } catch (Throwable t) {
            System.err.println("Failed: unexpected exception type: " + t);
        }
    }

    private static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage instanceof CompletableFuture) {
            CompletableFuture<?> cf = (CompletableFuture<?>) stage;
            if (cf.isCompletedExceptionally()) {
                try {
                    cf.get();
                    System.err.println("Failed: get() should throw an exception");
                } catch (Throwable t) {
                    if (t.getCause() == ex || t.getCause().getCause() == ex) {
                        System.out.println("Passed: Exception propagated correctly");
                    } else {
                        System.err.println("Failed: Exception cause mismatch");
                    }
                }
            } else {
                System.err.println("Failed: CompletableFuture not completed exceptionally");
            }
        } else {
            System.err.println("Failed: Returned stage is not a CompletableFuture");
        }
    }
}
