/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.release() for expected UnsupportedOperationException
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.release();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type: " + t, t);
        }
    }
}
