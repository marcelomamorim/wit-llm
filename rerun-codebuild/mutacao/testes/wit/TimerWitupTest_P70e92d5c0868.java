/*
 * @test
 * @summary Tests for java.util.Timer.scheduleAtFixedRate method.
 * @run main TimerWitupTest_P70e92d5c0868
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P70e92d5c0868 {

    public static void main(String[] args) {
        testNegativeDelayThrows();
        testZeroPeriodThrows();
        testNegativePeriodThrows();
        testValidSchedule();
    }

    public static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, -1, 1000);
            System.out.println("Failed: Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public static void testZeroPeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 0, 0);
            System.out.println("Failed: Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public static void testNegativePeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
 }
        };
        try {
            timer.scheduleAtFixedRate(task, 1000, -5);
            System.out.println("Failed: Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public static void testValidSchedule() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // no-op
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 10, 20);
            System.out.println("Passed: scheduleAtFixedRate with valid parameters");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
    }
}
