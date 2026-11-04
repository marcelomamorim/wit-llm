/*
 * @test
 * @summary WITUP-derived checks for AtomicLongFieldUpdater.newUpdater, which reaches the run() field lookup path
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    static class Node {
        volatile long count = 5L;
    }

    public static void main(String[] args) {
        testValidFieldUpdates();
        testNullFieldNameThrowsNpe();
        testMissingFieldWrapsNoSuchFieldException();
    }

    static void testValidFieldUpdates() {
        AtomicLongFieldUpdater<Node> updater = AtomicLongFieldUpdater.newUpdater(Node.class, "count");
        Node node = new Node();
        if (updater.get(node) != 5L) {
            throw new AssertionError("expected initial value 5 but was " + updater.get(node));
        }
        if (!updater.compareAndSet(node, 5L, 9L)) {
            throw new AssertionError("compareAndSet should succeed for matching expected value");
        }
        if (updater.get(node) != 9L) {
            throw new AssertionError("expected value 9 after CAS but was " + updater.get(node));
        }
    }

    static void testNullFieldNameThrowsNpe() {
        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null fieldName");
    }

    static void testMissingFieldWrapsNoSuchFieldException() {
        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "doesNotExist");
        } catch (RuntimeException expected) {
            Throwable cause = expected.getCause();
            if (!(cause instanceof NoSuchFieldException)) {
                throw new AssertionError("expected cause NoSuchFieldException but was " + cause);
            }
            return;
        }
        throw new AssertionError("expected RuntimeException for missing field");
    }
}
