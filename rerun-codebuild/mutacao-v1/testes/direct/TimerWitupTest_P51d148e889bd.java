/* @test    @summary Test Timer.schedule with valid delay and period    @run main TimerWitupTest_P51d148e889bd */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_P51d148e889bd {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private boolean executed = false;
            @Override
            public void run() {
                if (executed) {
                    throw new AssertionError("Task executed more than once");
                }
                executed = true;
            }
        };
        long delay = 100;
        long period = 200;
        try {
            timer.schedule(task, delay, period);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during schedule", e);
        }
        // Wait enough time for the task to execute at least once
        try {
            Thread.sleep(delay + period + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        // Check if task has run
        if (!task.executed) {
            throw new AssertionError("Task did not execute as scheduled");
        }
        // Cleanup
        timer.cancel();
    }
}
