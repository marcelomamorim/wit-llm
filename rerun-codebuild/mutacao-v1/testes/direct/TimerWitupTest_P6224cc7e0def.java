/* @test    @summary Test Timer.scheduleAtFixedRate for valid and invalid inputs    @run main TimerWitupTest_P6224cc7e0def */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P6224cc7e0def {

    public static void main(String[] args) {
        testValidScheduleAtFixedRate();
        testNegativeDelayThrows();
        testZeroPeriodThrows();
        testNegativePeriodThrows();
    }

    static void testValidScheduleAtFixedRate() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        try {
            timer.scheduleAtFixedRate(task, 100, 200);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid schedule", e);
        }
    }

    static void testNegativeDelayThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {}
        };
        boolean thrown = false;
        try {
            timer.scheduleAtFixedRate(task, -10, 100);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative delay");
        }
    }

    static void testZeroPeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {}
        };
        boolean thrown = false;
        try {
            timer.scheduleAtFixedRate(task, 100, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for zero period");
        }
    }

    static void testNegativePeriodThrows() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {}
        };
        boolean thrown = false;
        try {
            timer.scheduleAtFixedRate(task, 100, -50);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative period");
        }
    }
}
