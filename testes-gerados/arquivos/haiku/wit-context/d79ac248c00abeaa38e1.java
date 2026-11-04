/*
 * @test
 * @summary Witup-generated checks for Timer.schedule(TimerTask, Date, long) argument validation
 * @run main TimerWitupTest
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {

    public static void main(String[] args) {
        testNonPositivePeriodRejected();
        testNegativeFirstTimeRejected();
        testNullFirstTimeRejected();
        testValidScheduleAccepted();
    }

    static void testNonPositivePeriodRejected() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new NoopTask();
            Date when = new Date(System.currentTimeMillis() + 60000L);
            expectIllegalArgument(() -> timer.schedule(task, when, 0L));
            expectIllegalArgument(() -> timer.schedule(task, when, -1L));
        } finally {
            timer.cancel();
        }
    }

    static void testNegativeFirstTimeRejected() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new NoopTask();
            Date past = new Date(-1L);
            expectIllegalArgument(() -> timer.schedule(task, past, 1000L));
        } finally {
            timer.cancel();
        }
    }

    static void testNullFirstTimeRejected() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new NoopTask();
            expectNullPointer(() -> timer.schedule(task, null, 1000L));
        } finally {
            timer.cancel();
        }
    }

    static void testValidScheduleAccepted() {
        Timer timer = new Timer(true);
        try {
            TimerTask task = new NoopTask();
            Date when = new Date(System.currentTimeMillis() + 60000L);
            timer.schedule(task, when, 1000L);
        } finally {
            timer.cancel();
        }
    }

    static void expectIllegalArgument(Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void expectNullPointer(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    static class NoopTask extends TimerTask {
        @Override
        public void run() {
        }
    }
}
