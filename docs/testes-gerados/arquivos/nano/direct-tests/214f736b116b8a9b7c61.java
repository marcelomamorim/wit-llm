/* @test    @summary Test Timer(String, boolean) constructor with various parameters    @run main TimerWitupTest */

import java.util.Timer;

public class TimerWitupTest {

    public static void main(String[] args) {
        testTimerConstructorWithNameAndDaemon(true);
        testTimerConstructorWithNameAndDaemon(false);
    }

    private static void testTimerConstructorWithNameAndDaemon(boolean isDaemon) {
        String name = "TestTimer" + (isDaemon ? "Daemon" : "NonDaemon");
        Timer timer = new Timer(name, isDaemon);
        String threadName = timer.thread.getName();
        boolean threadIsDaemon = timer.thread.isDaemon();
        if (!threadName.equals(name)) {
            throw new AssertionError("Expected thread name: " + name + ", but was: " + threadName);
        }
        if (threadIsDaemon != isDaemon) {
            throw new AssertionError("Expected thread daemon: " + isDaemon + ", but was: " + threadIsDaemon);
        }
        timer.cancel();
    }
}
