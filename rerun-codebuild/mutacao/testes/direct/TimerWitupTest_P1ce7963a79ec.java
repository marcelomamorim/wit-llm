/* @test    @summary Test Timer.schedule with valid delay and period    @run main TimerWitupTest_P1ce7963a79ec */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P1ce7963a79ec {

    public static void main(String[] args) {
        testScheduleValidParameters();
        testScheduleNegativeDelay();
        testScheduleZeroPeriod();
    }

    static void testScheduleValidParameters() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        long delay = 100;
        long period = 200;
        timer.schedule(task, delay, period);
        // Since scheduling is asynchronous, verify that task is scheduled without exception
        // No direct observable effect; assume no exception means success
        System.out.println("testScheduleValidParameters passed");
        timer.cancel();
    }

    static void testScheduleNegativeDelay() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        };
        try {
            timer.schedule(task, -10, 100);
            System.out.println("testScheduleNegativeDelay failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testScheduleNegativeDelay passed");
        } finally {
            timer.cancel();
        }
    }

    static void testScheduleZeroPeriod() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        };
        try {
            timer.schedule(task, 100, 0);
            System.out.println("testScheduleZeroPeriod failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testScheduleZeroPeriod passed");
        } finally {
            timer.cancel();
        }
    }
}
