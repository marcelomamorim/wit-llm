/* @test    @summary Test scheduling with non-negative delay.    @run main TimerWitupTest */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {

    public static void main(String[] args) {
        testScheduleWithNegativeDelay();
        testScheduleWithZeroDelay();
        testScheduleWithPositiveDelay();
    }

    static void testScheduleWithNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, -1);
            System.out.println("Failed: Negative delay did not throw.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Negative delay threw IllegalArgumentException.");
        }
    }

    static void testScheduleWithZeroDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 0);
            System.out.println("Passed: Zero delay scheduled successfully.");
        } catch (Exception e) {
            System.out.println("Failed: Zero delay threw an exception.");
        }
    }

    static void testScheduleWithPositiveDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 100);
            System.out.println("Passed: Positive delay scheduled successfully.");
        } catch (Exception e) {
            System.out.println("Failed: Positive delay threw an exception.");
        }
    }
}
