/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, long, long) for delay and period validation
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) {
        Timer timer = new Timer();

        // Test valid schedule call
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        timer.schedule(task, 0L, 1L); // should not throw

        // Test negative delay throws IllegalArgumentException
        try {
            timer.schedule(task, -1L, 1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            if (!"Negative delay.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for negative delay: " + e.getMessage());
            }
        }

        // Test zero period throws IllegalArgumentException
        try {
            timer.schedule(task, 0L, 0L);
            throw new RuntimeException("Expected IllegalArgumentException for non-positive period");
        } catch (IllegalArgumentException e) {
            if (!"Non-positive period.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for non-positive period: " + e.getMessage());
            }
        }

        // Test negative period throws IllegalArgumentException
        try {
            timer.schedule(task, 0L, -1L);
            throw new RuntimeException("Expected IllegalArgumentException for non-positive period");
        } catch (IllegalArgumentException e) {
            if (!"Non-positive period.".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for non-positive period: " + e.getMessage());
            }
        }

        timer.cancel();
    }
}
