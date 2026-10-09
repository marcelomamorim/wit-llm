/*
 * @test
 * @summary Test CompletableFuture.orTimeout(long, TimeUnit) for null unit and normal usage
 * @run main CompletableFutureWitupTest_P88820d75939f
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P88820d75939f {
    public static void main(String[] args) {
        testOrTimeoutNullUnit();
        testOrTimeoutNormal();
    }

    private static void testOrTimeoutNullUnit() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1L, null);
            throw new AssertionError("Expected NullPointerException for null TimeUnit");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOrTimeoutNormal() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        CompletableFuture<Object> returned = cf.orTimeout(10L, TimeUnit.MILLISECONDS);
        if (returned != cf) {
            throw new AssertionError("orTimeout should return this CompletableFuture instance");
        }
        // The CompletableFuture is not completed yet, so result == null internally
        // We cannot assert internal state, but we can check that it is not done immediately
        if (cf.isDone()) {
            throw new AssertionError("CompletableFuture should not be done immediately after orTimeout");
        }
    }
}
