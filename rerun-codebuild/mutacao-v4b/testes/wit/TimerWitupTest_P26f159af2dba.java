/* @test
   @summary Tests the schedule method of java.util.Timer for various inputs.
   @run main TimerWitupTest_P26f159af2dba
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
public class TimerWitupTest_P26f159af2dba {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        // Valid case: delay and period positive
        try {
            timer.schedule(task, 1000L, 2000L);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid schedule", e);
        }
        // Edge case: delay negative, expect IllegalArgumentException
        try {
            timer.schedule(task, -1L, 1000L);
            throw new AssertionError("Expected IllegalArgumentException for negative delay");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Edge case: period zero, expect IllegalArgumentException
        try {
            timer.schedule(task, 1000L, 0L);
            throw new AssertionError("Expected IllegalArgumentException for zero period");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Edge case: period negative, expect IllegalArgumentException
        try {
            timer.schedule(task, 1000L, -5L);
            throw new AssertionError("Expected IllegalArgumentException for negative period");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Additional test: large delay and period
        try {
            timer.schedule(task, Long.MAX_VALUE - 1, Long.MAX_VALUE - 1);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large delay and period", e);
        }
    }
}
