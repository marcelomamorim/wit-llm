/*
 * @test
 * @summary Test Timer(String name, boolean isDaemon) constructor for name null and normal usage
 * @run main TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithNullName();
    }

    private static void testConstructorWithValidName() {
        String name = "TestTimer";
        boolean isDaemon = true;
        Timer timer = new Timer(name, isDaemon);
        // The thread name should be set to the given name
        // and the timer should start without exceptions
        // No direct getter for thread name, so rely on no exception and no deadlock
        timer.cancel(); // cleanup
    }

    private static void testConstructorWithNullName() {
        // According to WIT expath, if name is null and security is null, NPE expected
        // In current JDK 11 code, thread.setName(name) will throw NullPointerException if name is null
        boolean isDaemon = false;
        try {
            new Timer(null, isDaemon);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception caught
        }
    }
}
