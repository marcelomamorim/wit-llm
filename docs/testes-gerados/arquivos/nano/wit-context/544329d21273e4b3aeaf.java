/* @test    @summary Test for CompletableFuture.isDone() method to observe exception behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsDoneThrowsException();
    }

    public static void testIsDoneThrowsException() {
        CompletableFuture<?> cf = new CompletableFuture<>();
        try {
            cf.isDone();
            System.out.println("Test Failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test Passed: Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.out.println("Test Failed: Caught unexpected exception: " + t);
        }
    }
}
