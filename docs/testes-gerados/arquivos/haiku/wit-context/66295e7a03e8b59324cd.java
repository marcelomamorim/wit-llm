/*
 * @test
 * @summary Verify argument validation of Object.wait(long, int)
 * @run main ObjectWitupTest
 */

public class ObjectWitupTest {

    public static void main(String[] args) throws Exception {
        final Object lock = new Object();
        synchronized (lock) {
            expectIllegalArgument(lock, -1L, 0);
            expectIllegalArgument(lock, -1000L, 500);
            expectIllegalArgument(lock, 0L, -1);
            expectIllegalArgument(lock, 0L, 1000000);
            expectIllegalArgument(lock, 5L, Integer.MIN_VALUE);
            lock.wait(1L, 999999);
            lock.wait(1L, 0);
        }
    }

    static void expectIllegalArgument(Object lock, long timeoutMillis, int nanos)
            throws InterruptedException {
        try {
            lock.wait(timeoutMillis, nanos);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for "
                + timeoutMillis + ", " + nanos);
    }
}
