/*
 * @test
 * @summary Checks public argument validation and cancelled-timer behavior of Timer.schedule(TimerTask, long, long)
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectThrows(Class<? extends Throwable> type, Action action, String name) {
        Throwable thrown = null;
        try {
            action.run();
        } catch (Throwable t) {
            thrown = t;
        }
        if (thrown == null) {
            throw new RuntimeException(name + ": expected " + type.getName() + " but no exception was thrown");
        }
        if (!type.isInstance(thrown)) {
            throw new RuntimeException(name + ": expected " + type.getName() + " but got " + thrown);
        }
    }

    static TimerTask newTask() {
        return new TimerTask() {
            @Override
            public void run() {
            }
        };
    }

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer(true);
        try {
            expectThrows(IllegalArgumentException.class,
                    () -> timer.schedule(newTask(), -1L, 1000L),
                    "negative delay");

            expectThrows(IllegalArgumentException.class,
                    () -> timer.schedule(newTask(), Long.MIN_VALUE, 1000L),
                    "Long.MIN_VALUE delay");

            expectThrows(IllegalArgumentException.class,
                    () -> timer.schedule(newTask(), 3600000L, 0L),
                    "zero period");

            expectThrows(IllegalArgumentException.class,
                    () -> timer.schedule(newTask(), 3600000L, -1L),
                    "negative period");

            expectThrows(IllegalArgumentException.class,
                    () -> timer.schedule(newTask(), -5L, 0L),
                    "negative delay and zero period");

            timer.schedule(newTask(), 3600000L, 1000L);
        } finally {
            timer.cancel();
        }

        Timer cancelled = new Timer(true);
        cancelled.cancel();
        expectThrows(IllegalStateException.class,
                () -> cancelled.schedule(newTask(), 3600000L, 1000L),
                "schedule after cancel");
    }
}
