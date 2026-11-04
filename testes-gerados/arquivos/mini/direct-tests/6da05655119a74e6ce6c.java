/*
 * @test
 * @summary Test java.util.Timer.schedule(TimerTask, Date) basic behavior and edge cases
 * @run main java.util.TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        testScheduleWithValidDate();
        testScheduleWithPastDate();
        testScheduleWithNullTask();
        testScheduleWithNullDate();
    }

    static void testScheduleWithValidDate() throws Exception {
        final Timer timer = new Timer();
        final boolean[] ran = {false};
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        Date future = new Date(System.currentTimeMillis() + 100);
        timer.schedule(task, future);
        Thread.sleep(200);
        timer.cancel();
        if (!ran[0]) {
            throw new RuntimeException("Task did not run as scheduled");
        }
    }

    static void testScheduleWithPastDate() throws Exception {
        final Timer timer = new Timer();
        final boolean[] ran = {false};
        TimerTask task = new TimerTask() {
            public void run() {
                ran[0] = true;
            }
        };
        Date past = new Date(System.currentTimeMillis() - 1000);
        timer.schedule(task, past);
        Thread.sleep(100);
        timer.cancel();
        if (!ran[0]) {
            throw new RuntimeException("Task scheduled with past date did not run immediately");
        }
    }

    static void testScheduleWithNullTask() {
        Timer timer = new Timer();
        Date date = new Date();
        try {
            timer.schedule(null, date);
            throw new RuntimeException("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }

    static void testScheduleWithNullDate() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {}
        };
        try {
            timer.schedule(task, null);
            throw new RuntimeException("Expected NullPointerException for null date");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            timer.cancel();
        }
    }
}
