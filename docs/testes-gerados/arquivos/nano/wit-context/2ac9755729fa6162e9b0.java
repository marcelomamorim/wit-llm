/*
 * @test
 * @summary Test the Thread.start() method for normal behavior and IllegalThreadStateException
 * @run main ThreadWitupTest
 */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) {
        testNormalStart();
        testStartWhenNotNew();
    }

    private static void testNormalStart() {
        Thread thread = new Thread();
        try {
            thread.start();
        } catch (IllegalThreadStateException e) {
            throw new AssertionError("Unexpected IllegalThreadStateException on new thread", e);
        }
    }

    private static void testStartWhenNotNew() {
        Thread thread = new Thread();
        thread.start();
        boolean exceptionThrown = false;
        try {
            thread.start();
        } catch (IllegalThreadStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalThreadStateException when starting a thread twice");
        }
    }
}
