/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquireUninterruptibly() for expected UnsupportedOperationException
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type: " + t, t);
        }
    }
}
