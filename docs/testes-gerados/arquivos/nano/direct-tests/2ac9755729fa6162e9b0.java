/*
 * @test
 * @summary Tests the Thread.start() method for correct state transition and exception throwing.
 * @run main ThreadWitupTest
 */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) {
        testStartOnNewThread();
        testStartTwiceThrows();
    }

    static void testStartOnNewThread() {
        Thread t = new Thread();
        try {
            t.start();
        } catch (IllegalThreadStateException e) {
            throw new AssertionError("Starting a new thread should not throw exception", e);
        }
    }

    static void testStartTwiceThrows() {
        Thread t = new Thread();
        t.start();
        try {
            t.start();
            throw new AssertionError("Starting a thread twice should throw IllegalThreadStateException");
        } catch (IllegalThreadStateException e) {
            // expected
        }
    }
}
