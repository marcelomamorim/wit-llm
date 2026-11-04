/*
 * @test
 * @summary Verifies AtomicLongFieldUpdater.newUpdater reflective field lookup, validation and basic atomic operations via public API
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static class Node {
        public volatile long count = 5L;
        public long plain = 1L;
        public volatile int small = 2;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testBasicOperations() {
        AtomicLongFieldUpdater<Node> updater =
            AtomicLongFieldUpdater.newUpdater(Node.class, "count");
        Node n = new Node();
        check(updater.get(n) == 5L, "initial get");
        updater.set(n, 10L);
        check(updater.get(n) == 10L, "get after set");
        check(updater.compareAndSet(n, 10L, 20L), "CAS success expected");
        check(updater.get(n) == 20L, "value after successful CAS");
        check(!updater.compareAndSet(n, 10L, 30L), "CAS failure expected");
        check(updater.get(n) == 20L, "value unchanged after failed CAS");
        check(updater.incrementAndGet(n) == 21L, "incrementAndGet");
        check(updater.getAndAdd(n, 4L) == 21L, "getAndAdd returns previous value");
        check(updater.get(n) == 25L, "value after getAndAdd");
    }

    static void testMissingField() {
        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "missing");
            throw new RuntimeException("expected RuntimeException for missing field");
        } catch (RuntimeException e) {
            check(e.getCause() instanceof NoSuchFieldException,
                  "cause should be NoSuchFieldException");
        }
    }

    static void testNonVolatileField() {
        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "plain");
            throw new RuntimeException("expected IllegalArgumentException for non-volatile field");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNonLongField() {
        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "small");
            throw new RuntimeException("expected IllegalArgumentException for int field");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static void testWrongObjectType() {
        AtomicLongFieldUpdater<Node> updater =
            AtomicLongFieldUpdater.newUpdater(Node.class, "count");
        AtomicLongFieldUpdater raw = updater;
        try {
            raw.get(new Object());
            throw new RuntimeException("expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        testBasicOperations();
        testMissingField();
        testNonVolatileField();
        testNonLongField();
        testWrongObjectType();
    }
}
