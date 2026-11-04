/*
 * @test
 * @summary Testa o método runAsync de CompletableFuture para comportamento observável.
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testRunAsyncWithNullRunnable();
        testRunAsyncWithValidRunnable();
    }

    private static void testRunAsyncWithNullRunnable() {
        try {
            CompletableFuture<Void> cf = CompletableFuture.runAsync(null);
            System.out.println("FAIL: Expected NullPointerException for null runnable");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null runnable");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testRunAsyncWithValidRunnable() throws InterruptedException {
        final boolean[] executed = {false};
        Runnable r = () -> executed[0] = true;
        CompletableFuture<Void> cf = CompletableFuture.runAsync(r);
        cf.join();
        if (executed[0]) {
            System.out.println("PASS: Runnable executed successfully");
        } else {
            System.out.println("FAIL: Runnable was not executed");
        }
    }
}
