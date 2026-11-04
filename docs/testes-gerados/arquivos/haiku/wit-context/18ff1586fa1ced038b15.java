/*
 * @test
 * @summary Checks argument validation and post-cancel behavior of Timer.scheduleAtFixedRate(TimerTask, long, long)
 * @run main TimerWitupTest
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest {

    static void expectIAE(Timer t, TimerTask task, long delay, long period) {
        try {
            t.scheduleAtFixedRate(task, delay, period);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("IllegalArgumentException expected for delay=" + delay + " period=" + period);
    }

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            public void run() { }
        };

        Timer timer = new Timer(true);
        try {
            expectIAE(timer, task, -1L, 100L);
            expectIAE(timer, task, 0L, 0L);
            expectIAE(timer, task, 0L, -5L);
            timer.scheduleAtFixedRate(task, 0L, 1000L);
        } finally {
            timer.cancel();
        }

        Timer cancelled = new Timer(true);
        cancelled.cancel();
        try {
            cancelled.scheduleAtFixedRate(task, 0L, 1000L);
        } catch (IllegalStateException e) {
            return;
        }
        throw new AssertionError("IllegalStateException expected after cancel");
    }
}
