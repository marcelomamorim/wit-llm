/*
 * @test
 * @summary Verify AtomicLongFieldUpdater.newUpdater field lookup via the public API (run() path)
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static class Holder {
        public volatile long count;
        public volatile int other;
        public long plain;
    }

    public static void main(String[] args) throws Exception {
        testValidVolatileLongField();
        testWrongFieldType();
        testNonVolatileLongField();
        testMissingField();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidVolatileLongField() {
        AtomicLongFieldUpdater<Holder> updater =
            AtomicLongFieldUpdater.newUpdater(Holder.class, "count");
        check(updater != null, "updater must not be null");

        Holder h = new Holder();
        h.count = 5L;
        check(updater.compareAndSet(h, 5L, 7L), "CAS should succeed on matching value");
        check(updater.get(h) == 7L, "get should return 7 after CAS");
        updater.set(h, 9L);
        check(updater.get(h) == 9L, "get should return 9 after set");
        check(updater.addAndGet(h, 1L) == 10L, "addAndGet should return 10");
    }

    static void testWrongFieldType() {
        try {
            AtomicLongFieldUpdater.newUpdater(Holder.class, "other");
            throw new RuntimeException("expected IllegalArgumentException for int field");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNonVolatileLongField() {
        try {
            AtomicLongFieldUpdater.newUpdater(Holder.class, "plain");
            throw new RuntimeException("expected IllegalArgumentException for non-volatile field");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMissingField() {
        try {
            AtomicLongFieldUpdater.newUpdater(Holder.class, "doesNotExist");
            throw new RuntimeException("expected RuntimeException for missing field");
        } catch (RuntimeException e) {
            if (e instanceof IllegalArgumentException) {
                throw new RuntimeException("missing field must not be IllegalArgumentException", e);
            }
            check(e.getCause() instanceof NoSuchFieldException,
                  "cause should be NoSuchFieldException");
        }
    }
}
