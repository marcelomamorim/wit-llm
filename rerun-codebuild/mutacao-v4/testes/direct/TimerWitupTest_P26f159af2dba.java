/* @test
   @summary Tests Timer.schedule with valid and invalid parameters.
   @run main TimerWitupTest_P26f159af2dba
 */
import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P26f159af2dba {
    public static void main(String[] args) {
        testScheduleValid();
        testScheduleNegativeDelay();
        testScheduleNonPositivePeriod();
    }

    static void testScheduleValid() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        try {
            timer.schedule(task, 1000L, 2000L);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid schedule", e);
        }
    }

    static void testScheduleNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() { /* no-op */ }
        };
        try {
            timer.schedule(task, -1L, 1000L);
            throw new AssertionError("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testScheduleNonPositivePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() { /* no-op */ }
        };
        try {
            timer.schedule(task, 1000L, 0L);
            throw new AssertionError("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            timer.schedule(task, 1000L, -5L);
            throw new AssertionError("Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
