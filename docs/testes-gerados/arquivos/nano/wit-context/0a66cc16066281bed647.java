/* @test    @summary Test for CompletableFuture.thenRunAsync with null Runnable    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testThenRunAsyncWithNullRunnable();
    }

    public static void testThenRunAsyncWithNullRunnable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.thenRunAsync(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
