/* @test    @summary Test CompletableFuture.get with timeout throws UnsupportedOperationException    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetMethodThrowsUnsupported();
    }

    public static void testGetMethodThrowsUnsupported() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            future.get(1, TimeUnit.SECONDS);
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
