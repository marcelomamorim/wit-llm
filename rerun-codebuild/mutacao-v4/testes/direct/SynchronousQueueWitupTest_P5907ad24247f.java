/* @test
   @summary Tests the offer method of SynchronousQueue for null and non-null elements.
   @run main SynchronousQueueWitupTest_P5907ad24247f
 */
import java.util.concurrent.SynchronousQueue;
public class SynchronousQueueWitupTest_P5907ad24247f {
    public static void main(String[] args) {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        // Test that offer returns true when a non-null element is offered
        boolean result = queue.offer(new Object());
        if (!result) {
            throw new AssertionError("offer should return true for non-null element");
        }
        // Test that offer throws NullPointerException when null is offered
        try {
            queue.offer(null);
            throw new AssertionError("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
