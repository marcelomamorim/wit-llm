/* @test
   @summary Testa o método offer() de SynchronousQueue para entradas nulas.
   @run main SynchronousQueueWitupTest_P5907ad24247f
 */
import java.util.concurrent.SynchronousQueue;
public class SynchronousQueueWitupTest_P5907ad24247f {
    public static void main(String[] args) {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.offer(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
