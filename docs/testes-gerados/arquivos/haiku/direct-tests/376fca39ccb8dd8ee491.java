/*
 * @test
 * @summary ForkJoinTask.adapt(Runnable) wraps a runnable so that invoke, join and fork run it once and complete the task
 * @run main ForkJoinTaskWitupTest
 */
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) throws Exception {
        AtomicInteger counter = new AtomicInteger();
        ForkJoinTask<?> task = ForkJoinTask.adapt(() -> {
            counter.incrementAndGet();
        });

        if (task == null) {
            throw new AssertionError("adapt returned null");
        }
        if (counter.get() != 0) {
            throw new AssertionError("runnable executed before invoke: " + counter.get());
        }
        if (task.isDone()) {
            throw new AssertionError("task done before invoke");
        }

        task.invoke();
        if (counter.get() != 1) {
            throw new AssertionError("expected 1 run after invoke, got " + counter.get());
        }
        if (!task.isDone() || !task.isCompletedNormally()) {
            throw new AssertionError("task not completed normally after invoke");
        }
        if (task.getRawResult() != null) {
            throw new AssertionError("adapted runnable raw result should be null");
        }

        task.join();
        if (counter.get() != 1) {
            throw new AssertionError("completed task must not rerun, count=" + counter.get());
        }

        ForkJoinTask<?> forked = ForkJoinTask.adapt(() -> {
            counter.incrementAndGet();
        });
        forked.fork().join();
        if (counter.get() != 2) {
            throw new AssertionError("expected 2 runs after fork/join, got " + counter.get());
        }
        if (!forked.isDone()) {
            throw new AssertionError("forked task not done after join");
        }
    }
}
