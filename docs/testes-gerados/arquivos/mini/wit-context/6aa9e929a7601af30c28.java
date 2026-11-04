/*
 * @test
 * @summary Test ArrayBlockingQueue.iterator() throws UnsupportedOperationException
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        Iterator<Integer> it = queue.iterator();
        boolean unsupportedOperationThrown = false;
        try {
            it.remove(); // Itr.remove() throws UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            unsupportedOperationThrown = true;
        }
        if (!unsupportedOperationThrown) {
            throw new AssertionError("Expected UnsupportedOperationException from iterator().remove()");
        }
    }
}
