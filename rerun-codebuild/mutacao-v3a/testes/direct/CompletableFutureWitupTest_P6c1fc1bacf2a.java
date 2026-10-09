/* @test    @summary Test CompletableFuture.failedStage with non-null Throwable    @run main CompletableFutureWitupTest_P6c1fc1bacf2a */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P6c1fc1bacf2a {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    public static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            System.out.println("FAILED: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("PASSED: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAILED: Unexpected exception: " + t);
        }
    }

    public static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletableFuture<?> cf = CompletableFuture.failedStage(ex);
        if (cf.isDone() && !cf.isCompletedExceptionally()) {
            System.out.println("FAILED: CompletableFuture not completed exceptionally");
            return;
        }
        try {
            cf.get();
            System.out.println("FAILED: get() should throw an exception");
        } catch (Throwable t) {
            if (t.getCause() == ex || t.getCause().getClass() == ex.getClass()) {
                System.out.println("PASSED: get() throws the expected exception");
            } else {
                System.out.println("FAILED: get() threw unexpected exception: " + t);
            }
        }
    }
}
