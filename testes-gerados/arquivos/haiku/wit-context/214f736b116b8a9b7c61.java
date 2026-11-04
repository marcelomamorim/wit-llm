/*
 * @test
 * @summary WITUP-derived tests for Timer(String name, boolean isDaemon): null name NPE and thread name/daemon observability
 * @run main TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {

    public static void main(String[] args) throws Exception {
        testNamedDaemonTimer();
        testNamedNonDaemonTimer();
        testNullNameThrowsNPE();
    }

    static Thread findThread(String name) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (name.equals(t.getName())) {
                return t;
            }
        }
        return null;
    }

    static void testNamedDaemonTimer() {
        String name = "witup-timer-daemon";
        Timer timer = new Timer(name, true);
        try {
            Thread t = findThread(name);
            check(t != null, "timer thread with given name not found");
            check(t.isDaemon(), "timer thread should be daemon when isDaemon=true");
        } finally {
            timer.cancel();
        }
    }

    static void testNamedNonDaemonTimer() {
        String name = "witup-timer-nondaemon";
        Timer timer = new Timer(name, false);
        try {
            Thread t = findThread(name);
            check(t != null, "timer thread with given name not found");
            check(!t.isDaemon(), "timer thread should not be daemon when isDaemon=false");
        } finally {
            timer.cancel();
        }
    }

    static void testNullNameThrowsNPE() {
        try {
            new Timer(null, false);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
