/*
 * @test
 * @summary Tests AbstractExecutorService.submit(Runnable, T) observable behavior
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class SyncExecutor extends AbstractExecutorService {
        boolean shut;
        int newTaskForCalls;
        Object lastTask;

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            newTaskForCalls++;
            RunnableFuture<T> f = super.newTaskFor(runnable, value);
            lastTask = f;
            return f;
        }

        @Override
        public void shutdown() { shut = true; }

        @Override
        public List<Runnable> shutdownNow() {
            shut = true;
            return new ArrayList<>();
        }

        @Override
        public boolean isShutdown() { return shut; }

        @Override
        public boolean isTerminated() { return shut; }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) { return true; }

        @Override
        public void execute(Runnable command) { command.run(); }
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testReturnsFutureWithValue();
        testNullResultValue();
        testOverriddenNewTaskForIsUsed();
        testTaskExceptionStoredInFuture();
    }

    static void testNullTaskThrowsNPE() {
        SyncExecutor ex = new SyncExecutor();
        boolean thrown = false;
        try {
            ex.submit((Runnable) null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null task");
        check(ex.newTaskForCalls == 0, "newTaskFor must not be called for null task");
    }

    static void testReturnsFutureWithValue() throws Exception {
        SyncExecutor ex = new SyncExecutor();
        int[] ran = {0};
        Future<String> f = ex.submit(() -> ran[0]++, "done");
        check(ran[0] == 1, "task should have been executed once");
        check(f.isDone(), "future should be done after synchronous execution");
        check("done".equals(f.get()), "future should yield the given result");
    }

    static void testNullResultValue() throws Exception {
        SyncExecutor ex = new SyncExecutor();
        Future<Object> f = ex.submit(() -> { }, null);
        check(f.get() == null, "null result should be returned as null");
    }

    static void testOverriddenNewTaskForIsUsed() throws Exception {
        SyncExecutor ex = new SyncExecutor();
        Future<String> f = ex.submit(() -> { }, "v");
        check(ex.newTaskForCalls == 1, "newTaskFor should be called once");
        check(f == ex.lastTask, "returned future should be the one created by newTaskFor");
        check("v".equals(f.get()), "result should be preserved");
    }

    static void testTaskExceptionStoredInFuture() throws Exception {
        SyncExecutor ex = new SyncExecutor();
        Future<String> f = ex.submit(() -> { throw new IllegalStateException("boom"); }, "x");
        boolean wrapped = false;
        try {
            f.get();
        } catch (ExecutionException e) {
            wrapped = e.getCause() instanceof IllegalStateException;
        }
        check(wrapped, "task exception should be reported via ExecutionException");
    }
}
