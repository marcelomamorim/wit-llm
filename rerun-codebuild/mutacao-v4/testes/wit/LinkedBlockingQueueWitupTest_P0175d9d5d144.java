/* @test
   @summary Testa o método offer de LinkedBlockingQueue para entradas nulas e capacidade cheia.
   @run main LinkedBlockingQueueWitupTest_P0175d9d5d144
 */
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest_P0175d9d5d144 {
    public static void main(String[] args) throws Exception {
        testOfferNull();
        testOfferCapacityFull();
    }

    private static void testOfferNull() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            queue.offer(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testOfferCapacityFull() {
        // Cria uma fila com capacidade 1 e insere um elemento para encher a fila
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        queue.offer(1);
        // Tenta inserir outro elemento, que deve retornar false
        boolean result = queue.offer(2);
        if (result != false) {
            throw new AssertionError("Expected offer to return false when full");
        }
    }
}
