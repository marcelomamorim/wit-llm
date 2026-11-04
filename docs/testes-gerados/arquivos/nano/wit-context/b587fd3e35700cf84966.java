/* @test    @summary Test CompletableFuture.thenAccept for null Consumer    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testThenAcceptWithNullConsumer();
    }

    public static void testThenAcceptWithNullConsumer() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.thenAccept(null);
            // Se não lançar exceção, o teste falha
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Esperado
            System.out.println("NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
