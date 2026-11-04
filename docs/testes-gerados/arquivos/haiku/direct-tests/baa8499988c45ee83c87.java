/*
 * @test
 * @summary Verifies AbstractExecutorService.submit(Runnable) null handling, default FutureTask wrapping, and execution through execute(Runnable)
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class RecordingExecutor extends AbstractExecutorService {
        Runnable last;

        @Override
        public void execute(Runnable command) {
            last = command;
            command.run();
        }

        @Override
        public void shutdown() {
        }

        @Override
        public List<Runnable> shutdownNow() {
            return new ArrayList<>();
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return true;
        }
    }

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        RecordingExecutor ex = new RecordingExecutor();

        // null task must throw NullPointerException
        boolean npe = false;
        try {
            ex.submit((Runnable) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "submit(null) should throw NullPointerException");

        // normal submission: task runs, future is done, get() returns null
        boolean[] ran = {false};
        Runnable r = () -> { ran[0] = true; };
        Future<?> f = ex.submit(r);
        check(f != null, "submit should return a non-null Future");
        check(ran[0], "submitted runnable should have been run via execute");
        check(f.isDone(), "future should be done after synchronous execution");
        check(f.get() == null, "Future.get() for Runnable should return null");
        check(ex.last == f, "the future returned should be the task passed to execute");

        // exception thrown by task is captured by the future, not propagated by submit
        Runnable failing = () -> { throw new IllegalStateException("boom"); };
        Future<?> f2 = ex.submit(failing);
        check(f2.isDone(), "failing task future should be done");
        boolean wrapped = false;
        try {
            f2.get();
        } catch (ExecutionException e) {
            wrapped = e.getCause() instanceof IllegalStateException;
        }
        check(wrapped, "exception from task should be wrapped in ExecutionException with cause");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
