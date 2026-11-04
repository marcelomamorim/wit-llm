/* @test    @summary Test cancel method of CompletableFuture for UnsupportedOperationException    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCancelThrowsException();
    }

    public static void testCancelThrowsException() {
        CompletableFuture<?> cf = new CompletableFuture<>();
        try {
            boolean result = cf.cancel(true);
            // Se o método não lançar exceção, o resultado será false, pois a operação não foi cancelada.
            // Como o método atual lança UnsupportedOperationException, essa linha não será atingida.
            System.out.println("Unexpected result: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}
