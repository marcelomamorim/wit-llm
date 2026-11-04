/*
 * @test
 * @summary Witup-generated tests for Cleaner.create(ThreadFactory)
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.concurrent.atomic.AtomicInteger;

public class CleanerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThreadFactoryThrowsNPE();
        testThreadFactoryIsUsed();
        testCleanRunsActionOnlyOnce();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThreadFactoryThrowsNPE() {
        try {
            Cleaner.create(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null ThreadFactory");
    }

    static void testThreadFactoryIsUsed() {
        AtomicInteger calls = new AtomicInteger();
        Cleaner cleaner = Cleaner.create(r -> {
            calls.incrementAndGet();
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        check(cleaner != null, "create must return a non-null Cleaner");
        check(calls.get() >= 1, "ThreadFactory.newThread should be invoked by create");
    }

    static void testCleanRunsActionOnlyOnce() {
        AtomicInteger calls = new AtomicInteger();
        Cleaner cleaner = Cleaner.create(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        Object obj = new Object();
        Cleaner.Cleanable handle = cleaner.register(obj, () -> calls.incrementAndGet());
        handle.clean();
        handle.clean();
        check(calls.get() == 1, "Cleaning action must run at most once, ran " + calls.get() + " times");
    }
}
