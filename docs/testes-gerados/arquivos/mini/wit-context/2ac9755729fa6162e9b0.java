/*
 * @test
 * @summary Test java.lang.Thread.start() for IllegalThreadStateException on invalid threadStatus
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) {
        testStartThrowsIfThreadStatusNotZero();
        testStartSucceedsIfThreadStatusZero();
    }

    private static void testStartThrowsIfThreadStatusNotZero() {
        Thread t = new Thread();
        try {
            // Use reflection to set threadStatus to non-zero to simulate started thread
            java.lang.reflect.Field statusField = Thread.class.getDeclaredField("threadStatus");
            statusField.setAccessible(true);
            statusField.setInt(t, 1); // non-zero to trigger exception

            try {
                t.start();
                throw new RuntimeException("Expected IllegalThreadStateException not thrown");
            } catch (IllegalThreadStateException expected) {
                // expected
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }

    private static void testStartSucceedsIfThreadStatusZero() {
        Thread t = new Thread();
        try {
            // threadStatus should be zero initially, so start() should succeed
            t.start();
            // Wait for thread to die to avoid interference
            t.join();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during start", e);
        }
    }
}
