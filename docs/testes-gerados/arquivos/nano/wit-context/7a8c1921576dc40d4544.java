/* @test    @summary Test for CompletableFuture.join() method to verify exception behavior.    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testJoinThrowsUnsupportedOperationException();
    }

    public static void testJoinThrowsUnsupportedOperationException() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.join();
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, teste passa
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
