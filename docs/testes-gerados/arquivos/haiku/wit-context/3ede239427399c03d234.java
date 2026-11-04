/*
 * @test
 * @summary Witup tests for Executors.unconfigurableScheduledExecutorService
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testWrapperIsNewAndNotConfigurable();
        testDelegatesScheduling();
        testShutdownPropagates();
    }

    static void testNullThrowsNPE() {
        boolean thrown = false;
        try {
            Executors.unconfigurableScheduledExecutorService(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null executor");
        }
    }

    static void testWrapperIsNewAndNotConfigurable() {
        ScheduledExecutorService base = Executors.newSingleThreadScheduledExecutor();
        try {
            ScheduledExecutorService wrapper =
                Executors.unconfigurableScheduledExecutorService(base);
            if (wrapper == null) {
                throw new RuntimeException("wrapper must not be null");
            }
            if (wrapper == base) {
                throw new RuntimeException("wrapper must be a distinct object");
            }
            if (wrapper instanceof ScheduledThreadPoolExecutor) {
                throw new RuntimeException("wrapper must hide the implementation type");
            }
        } finally {
            base.shutdownNow();
        }
    }

    static void testDelegatesScheduling() throws Exception {
        ScheduledExecutorService base = Executors.newSingleThreadScheduledExecutor();
        try {
            ScheduledExecutorService wrapper =
                Executors.unconfigurableScheduledExecutorService(base);
            Callable<Integer> task = () -> 42;
            Integer result = wrapper.schedule(task, 0, TimeUnit.MILLISECONDS).get();
            if (result.intValue() != 42) {
                throw new RuntimeException("unexpected result: " + result);
            }
        } finally {
            base.shutdownNow();
        }
    }

    static void testShutdownPropagates() {
        ScheduledExecutorService base = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService wrapper =
            Executors.unconfigurableScheduledExecutorService(base);
        wrapper.shutdown();
        if (!base.isShutdown()) {
            throw new RuntimeException("shutdown via wrapper must affect delegate");
        }
        if (!wrapper.isShutdown()) {
            throw new RuntimeException("wrapper must report shutdown state");
        }
    }
}
