/* @test    @summary Tests for Timer.scheduleAtFixedRate to verify exception handling and behavior    @run main TimerWitupTest_P8adc9c0f4c4a */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P8adc9c0f4c4a {

    public static void main(String[] args) {
        testNegativeDelay();
        testZeroPeriod();
        testNegativePeriod();
        testValidScheduling();
    }

    private static void testNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() { /* no-op */ }
        };
        try {
            timer.scheduleAtFixedRate(task, -1000L, 1000L);
            System.err.println("Failed: Negative delay should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() { /* no-op */ }
        };
        try {
            timer.scheduleAtFixedRate(task, 0L, 0L);
            System.err.println("Failed: Zero period should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() { /* no-op */ }
        };
        try {
            timer.scheduleAtFixedRate(task, 1000L, -500L);
            System.err.println("Failed: Negative period should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidScheduling() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() { /* no-op */ }
        };
        try {
            timer.scheduleAtFixedRate(task, 100L, 200L);
            System.out.println("Scheduling succeeded with valid parameters.");
            timer.cancel();
        } catch (Exception e) {
            System.err.println("Failed: Valid scheduling threw exception: " + e);
        }
    }
}
