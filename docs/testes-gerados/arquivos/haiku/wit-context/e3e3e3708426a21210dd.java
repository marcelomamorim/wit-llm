/*
 * @test
 * @summary Witup generated tests for Timer(String name) constructor
 * @run main TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNamedTimerThreadIsStarted();
    }

    static void testNullNameThrowsNPE() {
        try {
            new Timer((String) null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }
    }

    static void testNamedTimerThreadIsStarted() {
        String name = "witup-timer-name-check";
        Timer timer = new Timer(name);
        try {
            boolean found = false;
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (name.equals(t.getName()) && t.isAlive()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("Timer thread with given name not found alive");
            }
        } finally {
            timer.cancel();
        }
    }
}
