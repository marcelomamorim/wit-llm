/*
 * @test    @summary Test for CompletableFuture.get(long, TimeUnit) throwing UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetThrowsUnsupportedOperationException();
    }

    public static void testGetThrowsUnsupportedOperationException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.get(1, TimeUnit.MILLISECONDS);
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
