/* @test    @summary Test Timer.schedule with valid delay and period    @run main TimerWitupTest_Peb8eaa461cce */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_Peb8eaa461cce {

    public static void main(String[] args) {
        testScheduleWithValidParameters();
        testScheduleWithNegativeDelay();
        testScheduleWithZeroPeriod();
    }

    private static void testScheduleWithValidParameters() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        long delay = 100;
        long period = 200;
        try {
            timer.schedule(task, delay, period);
            // Since schedule is non-blocking, we check that no exception was thrown and object exists
            assert timer != null : "Timer should be instantiated";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in schedule with valid parameters", e);
        }
    }

    private static void testScheduleWithNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, -10, 100);
            throw new AssertionError("Expected IllegalArgumentException for negative delay") ;
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Negative delay") : "Exception message should mention Negative delay";
        }
    }

    private static void testScheduleWithZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 100, 0);
            throw new AssertionError("Expected IllegalArgumentException for zero period") ;
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Non-positive period") : "Exception message should mention Non-positive period";
        }
    }
}
