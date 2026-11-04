/*
 * @test
 * @summary Test java.lang.Thread.start() method behavior
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) throws Exception {
        testStartOnNewThreadSucceeds();
        testStartOnAlreadyStartedThreadThrows();
    }

    private static void testStartOnNewThreadSucceeds() throws Exception {
        final Thread t = new Thread();
        // The thread is new, so start() should succeed without exception
        t.start();
        // Wait for thread to terminate to avoid interference with other tests
        t.join();
    }

    private static void testStartOnAlreadyStartedThreadThrows() throws Exception {
        final Thread t = new Thread();
        t.start();
        t.join();

        boolean threw = false;
        try {
            t.start();
        } catch (IllegalThreadStateException ex) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalThreadStateException when starting a thread twice");
        }
    }
}
