/* @test    @summary Test scheduling with valid parameters and exception cases for Timer.schedule()    @run main TimerWitupTest_P51d148e889bd */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P51d148e889bd {

    public static void main(String[] args) {
        testValidSchedule();
        testNegativeDelay();
        testZeroOrNegativePeriod();
        testSystemCurrentTimeMillisLessThanNegativeDelay();
    }

    static void testValidSchedule() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 100, 200);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid schedule", e);
        }
    }

    static void testNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, -1, 100);
            throw new AssertionError("Expected IllegalArgumentException for negative delay not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative delay", e);
        }
    }

    static void testZeroOrNegativePeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            timer.schedule(task, 100, 0);
            throw new AssertionError("Expected IllegalArgumentException for zero period not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero period", e);
        }
        try {
            timer.schedule(task, 100, -5);
            throw new AssertionError("Expected IllegalArgumentException for negative period not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative period", e);
        }
    }

    static void testSystemCurrentTimeMillisLessThanNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // no-op
            }
        };
        long delay = 100;
        long currentTime = System.currentTimeMillis();
        long triggerTime = currentTime + delay;
        if (triggerTime < 0) {
            try {
                timer.schedule(task, delay, 200);
            } catch (IllegalArgumentException e) {
                // expected if triggerTime overflows to negative
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception when currentTimeMillis < -delay", e);
            }
        }
    }
}
