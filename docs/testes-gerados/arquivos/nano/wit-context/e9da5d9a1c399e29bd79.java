/* @test    @summary Test for CompletableFuture.get() method to observe exception behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetThrowsUnsupportedOperationException();
    }

    public static void testGetThrowsUnsupportedOperationException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.get();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
